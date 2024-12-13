package com.barengsaya.dentassist.view.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.barengsaya.dentassist.MainActivity
import com.barengsaya.dentassist.data.pref.UserPreference
import com.barengsaya.dentassist.data.pref.dataStore
import com.barengsaya.dentassist.databinding.ActivityCameraBinding
import com.barengsaya.dentassist.di.Injection
import com.barengsaya.dentassist.view.ViewModelFactory
import com.barengsaya.dentassist.view.predict.PredictActivity
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.UUID

@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var viewModel: CameraViewModel


    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setNavigationOnClickListener {
            onBackPressed()
        }

        val repository = Injection.provideRepository(this)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CameraViewModel::class.java]

        viewModel.predictResult.observe(this) { result ->
            result.onSuccess { response ->
                val intent = Intent(this, PredictActivity::class.java)
                intent.putExtra("PREDICT_RESULT", response.data)
                startActivity(intent)
            }
            result.onFailure { error ->
                Toast.makeText(this, "Upload gagal: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }


        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            launchImageCrop(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let { uri ->
                launchImageCrop(uri)
            }
        }
    }

    private fun launchImageCrop(uri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "${UUID.randomUUID()}.jpg"))
        val options = UCrop.Options().apply {
            setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.SCALE)
            setToolbarTitle("Crop Image")
            setFreeStyleCropEnabled(true)
            setToolbarColor(ContextCompat.getColor(this@CameraActivity, android.R.color.white))
            setStatusBarColor(ContextCompat.getColor(this@CameraActivity, android.R.color.white))
            setActiveControlsWidgetColor(ContextCompat.getColor(this@CameraActivity, android.R.color.white))
        }

        UCrop.of(uri, destinationUri)
            .withOptions(options)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1080, 1080)
            .start(this)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val resultUri: Uri? = UCrop.getOutput(data!!)
            if (resultUri != null) {
                croppedImageUri = resultUri
                showCroppedImage()
            }
        } else if (requestCode == UCrop.RESULT_ERROR) {
            val cropError: Throwable? = UCrop.getError(data!!)
            cropError?.let { Log.e("UCrop Error", it.message ?: "Unknown error") }
        }
    }

    private fun showCroppedImage() {
        croppedImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        if (croppedImageUri != null) {
            val file = File(croppedImageUri!!.path!!)
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageBody = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val userPreference = UserPreference.getInstance(dataStore)
            lifecycleScope.launch {
                userPreference.getSession().collect { userModel ->
                    val idUserRequestBody = userModel.idUser.toRequestBody("text/plain".toMediaTypeOrNull())

                    // Call predict API
                    viewModel.predict(imageBody, idUserRequestBody)
                }
            }
        } else {
            Toast.makeText(this, "Pilih gambar terlebih dahulu!", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        super.onBackPressed()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
