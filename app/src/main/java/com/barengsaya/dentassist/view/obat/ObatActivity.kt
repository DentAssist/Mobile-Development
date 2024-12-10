package com.barengsaya.dentassist.view.obat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.barengsaya.dentassist.databinding.ActivityObatBinding
import com.barengsaya.dentassist.view.ViewModelFactory

class ObatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityObatBinding
    private val viewModel: ProductViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObatBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}