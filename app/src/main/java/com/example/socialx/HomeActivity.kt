package com.example.socialx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialx.adapters.NewsAdapter
import com.example.socialx.databinding.ActivityHomeBinding
import com.example.socialx.models.News
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class  HomeActivity : AppCompatActivity() {
    lateinit var adapter:NewsAdapter
    var binding:ActivityHomeBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        getNews()
    }

    private fun getNews() {
        val news=NewsService.newsInstance.getHeadLines("in",1)
        news.enqueue(object :retrofit2.Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
               val news:News?=response.body()
               if(news!=null){
                   Log.d("api",news.toString())
                   adapter= NewsAdapter(this@HomeActivity,news.articles)
                   binding?.newsList!!.adapter=adapter
                   binding?.newsList!!.layoutManager=LinearLayoutManager(this@HomeActivity)
               }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("api","Error in fetching News")
            }

        })
    }
}