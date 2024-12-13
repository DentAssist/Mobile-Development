package com.barengsaya.dentassist.view.predict

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barengsaya.dentassist.data.api.response.ArticlesItem
import com.barengsaya.dentassist.databinding.ItemArticlePredictBinding

class PredictArticleAdapter(
    private val articles: List<ArticlesItem>
) : RecyclerView.Adapter<PredictArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private val binding: ItemArticlePredictBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesItem) {
            binding.articleTitle.text = article.name
            binding.articleLink.text = article.link
            binding.articleLink.setOnClickListener {

                val url = article.link
                if (!url.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticlePredictBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size
}
