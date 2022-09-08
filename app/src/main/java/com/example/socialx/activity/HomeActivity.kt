package com.example.socialx.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialx.NewsService
import com.example.socialx.adapters.NewsAdapter
import com.example.socialx.databinding.ActivityHomeBinding
import com.example.socialx.models.Articles
import com.example.socialx.models.News
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Response
import java.util.Locale.filter
import kotlin.collections.ArrayList

class  HomeActivity : AppCompatActivity() {
    private lateinit var adapter:NewsAdapter
    private  var newsList:ArrayList<Articles> =ArrayList()
    var binding:ActivityHomeBinding?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        getSupportActionBar()?.hide();
        auth=FirebaseAuth.getInstance()
        getNews()
        binding?.logoutButton?.setOnClickListener{
            auth.signOut()
            Toast.makeText(this,"Logout Successful",Toast.LENGTH_SHORT).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding?.searchText?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p1: String?): Boolean {
                adapter.filter.filter(p1)
                return true
            }

        })


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
               }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("api","Error in fetching News")
            }

        })
    }
}