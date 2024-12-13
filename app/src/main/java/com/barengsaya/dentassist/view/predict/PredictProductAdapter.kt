package com.barengsaya.dentassist.view.predict

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.data.api.response.ProductsItem
import com.barengsaya.dentassist.databinding.ItemProductPredictBinding
import com.bumptech.glide.Glide

class PredictProductAdapter(
    private val products: List<ProductsItem>
) : RecyclerView.Adapter<PredictProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: ItemProductPredictBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductsItem) {
            binding.productName.text = product.name ?: ""
            binding.productHarga.text = "Harga: Rp.${product.price ?: 0}"

            Glide.with(binding.productImage.context)
                .load(product.linkPhoto)
                .placeholder(R.drawable.image_preview)
                .error(R.drawable.image_preview)
                .into(binding.productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductPredictBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount() = products.size
}
