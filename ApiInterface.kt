package com.example.imdb_data

import android.widget.EditText
import android.widget.TextView
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("?apikey=c0d64821")
    fun getData(@Query("i") i:String):Call<responceDataClass>

}