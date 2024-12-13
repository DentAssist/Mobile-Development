package com.barengsaya.dentassist.view.history

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.barengsaya.dentassist.R
import com.barengsaya.dentassist.data.api.response.DataItemHistory
import com.barengsaya.dentassist.databinding.ItemHistoryBinding
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class HistoryAdapter : ListAdapter<DataItemHistory, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistory>() {
            override fun areItemsTheSame(oldItem: DataItemHistory, newItem: DataItemHistory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemHistory, newItem: DataItemHistory): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemHistory) {
            binding.jenisPenyakit.text = data.label
            binding.akurasiPenyakit.text= Html.fromHtml(
                "${String.format("%.2f", data.confidenceScore)}%"
            )
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .placeholder(R.drawable.image_preview)
                .error(R.drawable.image_preview)
                .into(binding.gambarPenyakit)



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}
