package com.barengsaya.dentassist.view.clinik

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.barengsaya.dentassist.databinding.ActivityClinikBinding
import com.barengsaya.dentassist.view.ViewModelFactory

class ClinikActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClinikBinding
    private val viewModel: ClinikViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinikBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}
