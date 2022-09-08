package com.example.socialx.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialx.R
import com.example.socialx.models.Articles
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(var context: Context,var articles: ArrayList<Articles>):RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>(),Filterable{
    class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var author=itemView.findViewById<TextView>(R.id.news_source)
        var discription=itemView.findViewById<TextView>(R.id.news_description)
        var image=itemView.findViewById<ImageView>(R.id.news_image)
        var timeOfPublished=itemView.findViewById<TextView>(R.id.news_published)
        val title=itemView.findViewById<TextView>(R.id.news_title)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_show,parent,false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article:Articles=articles[position]
        holder.author.text=article.author
        holder.title.text=article.title
        holder.discription.text=article.description
        holder.timeOfPublished.text=article.publishedAt
        Glide.with(context).load(article.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun getFilter(): Filter=newsFilter
    private val newsFilter:Filter=object :Filter(){
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList: ArrayList<Articles> = ArrayList()
            if (p0 == null || p0.isBlank()) {
                filteredList.addAll(articles)
            } else {
                val filterPattern = p0.toString().lowercase(Locale.getDefault()).trim()
                for (item in articles) {
                    if (item.title.lowercase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            articles.clear()
            articles.addAll(p1?.values as ArrayList<Articles>)
            notifyDataSetChanged()
        }
    }


}