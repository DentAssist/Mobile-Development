package com.dicoding.myevent.ui.home


import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.myevent.data.model.EventItem
import com.dicoding.myevent.databinding.ItemEventBinding
import com.bumptech.glide.Glide

class EventListAdapter : ListAdapter<EventItem, EventListViewHolder>(DIFF_CALLBACK) {
//binding ini digunakan untuk menghubungkan layout dengan fragment
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val eventItem = getItem(position)
        holder.bindList(eventItem)
    }
//untuk menampilakn layout
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventItem>() {
            override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
                return oldItem == newItem
            }
        }
    }


}
//binding ini digunakan untuk menghubungkan layout dengan fragment
class EventListViewHolder(private val binding: ItemEventBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindList(item: EventItem) = with(binding) {
        Glide.with(root.context)
            .load(item.imageLogo)
            .into(ivBanner)

        tvTitle.text = item.name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDesc.text = Html.fromHtml(item.summary, Html.FROM_HTML_MODE_COMPACT)
        }
    }

}