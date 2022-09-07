package com.example.socialx.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialx.NewsService
import com.example.socialx.adapters.NewsAdapter
import com.example.socialx.databinding.ActivityHomeBinding
import com.example.socialx.models.Articles
import com.example.socialx.models.News
import retrofit2.Call
import retrofit2.Response
import kotlin.collections.ArrayList

class  HomeActivity : AppCompatActivity() {
    private lateinit var adapter:NewsAdapter
    private lateinit var newsList:ArrayList<Articles>
    private lateinit var searchList:ArrayList<Articles>
    var binding:ActivityHomeBinding?=null
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        newsList= ArrayList()
        searchList= ArrayList()
        binding?.searchText?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filter(p0)
                return true
            }

        })
        getNews()
    }

    private fun getNews() {
        val news= NewsService.newsInstance.getHeadLines("in",1)
        news.enqueue(object :retrofit2.Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
               val news:News?=response.body()
               if(news!=null){
                   Log.d("api",news.toString())
                   newsList=news.articles
                   adapter= NewsAdapter(this@HomeActivity, news.articles)
                   binding?.newsList!!.adapter=adapter
                   binding?.newsList!!.layoutManager=LinearLayoutManager(this@HomeActivity)
                   adapter.notifyDataSetChanged()
               }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("api","Error in fetching News")
            }

        })
    }
    private fun filter(text:String?){
        searchList.clear()
        for(article:Articles in newsList){
            if(article.title!!.lowercase().contains(text!!.lowercase())){
                searchList?.add(article)
            }
        }
        if(searchList!!.isEmpty()){
            Toast.makeText(this,"Data not found",Toast.LENGTH_SHORT).show()
        }else{
            adapter.updateNews(searchList)
        }
    }
}