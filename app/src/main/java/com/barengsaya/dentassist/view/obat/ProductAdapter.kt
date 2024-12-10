package com.barengsaya.dentassist.view.obat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barengsaya.dentassist.databinding.ItemProductBinding
import com.barengsaya.dentassist.data.api.response.ProductDataItem
import com.bumptech.glide.Glide

class ProductAdapter(private val products: List<ProductDataItem>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDataItem) {
            binding.apply {
                productName.text = product.name
                productHarga.text = "Rp. ${product.price}"
                Glide.with(itemView.context).load(product.linkPhoto).into(imageObat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}
