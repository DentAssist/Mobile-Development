package com.barengsaya.dentassist.view.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.barengsaya.dentassist.databinding.ActivitySignupBinding
import com.barengsaya.dentassist.view.ViewModelFactory
import com.barengsaya.dentassist.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val signupViewModel: SignupViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        observeViewModel()
        setupListeners()
    }
    private fun setupListeners() {
        binding.punyaAkun.setOnClickListener {
            Intent(this@SignupActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val username = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val city = binding.kotaEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || city.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email tidak valid!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8 || !password.matches(Regex(".*[A-Za-z].*")) || !password.matches(Regex(".*\\d.*"))) {
                Toast.makeText(
                    this,
                    "Password harus minimal 8 karakter, mengandung huruf, dan angka!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            signupViewModel.signup(username, email, password, city)
        }
    }


    private fun observeViewModel() {
        signupViewModel.signupResult.observe(this) { response ->
            if (response != null) {
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage(response.message ?: "Pendaftaran berhasil! Silahkan login.")
                    setPositiveButton("Lanjut") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }
        }

        signupViewModel.errorMessage.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

        signupViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
