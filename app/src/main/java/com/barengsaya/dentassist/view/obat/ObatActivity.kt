package com.barengsaya.dentassist.view.obat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.barengsaya.dentassist.MainActivity
import com.barengsaya.dentassist.databinding.ActivityObatBinding
import com.barengsaya.dentassist.view.ViewModelFactory

@Suppress("DEPRECATION")
class ObatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityObatBinding
    private val viewModel: ProductViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setNavigationOnClickListener {
            onBackPressed()
        }
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    Toast.makeText(this@ObatActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }
        setupObserver()
        viewModel.fetchProducts()
    }
    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { products ->
                products?.let {
                    binding.rvProduct.apply {
                        layoutManager = LinearLayoutManager(this@ObatActivity)
                        adapter = ProductAdapter(products)
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