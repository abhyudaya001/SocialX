package com.example.socialx

import com.example.socialx.models.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL="https://newsapi.org/"
const val API_KEY="045a2fcd9b7f4780919ef2700793b3b4"
interface NewsInterface {
@GET("v2/top-headlines?apikey=$API_KEY")
fun getHeadLines(@Query("country")country:String,@Query("page")page:Int):Call<News>
}
object NewsService{
    val newsInstance: NewsInterface
    init {
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance=retrofit.create(NewsInterface::class.java)
    }
}