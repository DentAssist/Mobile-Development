package com.barengsaya.dentassist.view.clinik

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.barengsaya.dentassist.MainActivity
import com.barengsaya.dentassist.databinding.ActivityClinikBinding
import com.barengsaya.dentassist.view.ViewModelFactory

@Suppress("DEPRECATION")
class ClinikActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClinikBinding
    private val viewModel: ClinikViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinikBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setNavigationOnClickListener {
            onBackPressed()
        }

        setupObserver()
        viewModel.fetchClinics()
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.clinics.collect { response ->
                response?.data?.let { clinics ->
                    binding.rvClinics.apply {
                        layoutManager = LinearLayoutManager(this@ClinikActivity)
                        adapter = ClinikAdapter(clinics.filterNotNull())
                    }
                }
            }
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

}
