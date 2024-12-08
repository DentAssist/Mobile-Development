package com.barengsaya.dentassist.view.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.data.api.response.Data
import com.barengsaya.dentassist.databinding.FragmentProfileBinding
import com.barengsaya.dentassist.view.welcome.WelcomeActivity
import com.barengsaya.dentassist.view.ViewModelFactory
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_IMAGE_GALLERY = 101
    }

    private var selectedImageUri: Uri? = null
    private var capturedImageBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ftProfile.setOnClickListener {
            showImagePickerOptions()
        }
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                activity?.finish()
            } else {
                viewModel.fetchUserProfile(user.idUser)
            }
        }

        viewModel.userProfile.observe(viewLifecycleOwner) { response ->
            response.data?.let { data ->
                updateUI(data)
            }
            binding.progressBar.visibility = View.GONE
        }

        binding.simpanButton.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val city = binding.cityEditText.text.toString()

            if (username.isEmpty() || email.isEmpty() || city.isEmpty()) {
                Toast.makeText(requireContext(), "Semua data harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE

            viewModel.getSession().observe(viewLifecycleOwner) { user ->
                viewModel.updateUserProfile(user.idUser, username, email, city)

                viewModel.userProfile.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        setupView()
        setupAction()
    }


    private fun updateUI(data: Data) {
        binding.nameEditText.setText(data.username)
        binding.emailEditText.setText(data.email)
        binding.cityEditText.setText(data.city)

        Glide.with(this)
            .load(data.profileImage)
            .placeholder(R.drawable.profile_default)
            .into(binding.ftProfile)
    }



    private fun showImagePickerOptions() {
        val options = arrayOf("Ambil Foto", "Pilih dari Galeri")
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Pilih Gambar")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkCameraPermissionAndStart()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun checkCameraPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun startCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    capturedImageBitmap = imageBitmap
                    binding.ftProfile.setImageBitmap(imageBitmap)
                }
                REQUEST_IMAGE_GALLERY -> {
                    val selectedUri = data?.data
                    selectedUri?.let {
                        selectedImageUri = it
                        binding.ftProfile.setImageURI(selectedUri)
                    }
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        activity?.actionBar?.hide()
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
