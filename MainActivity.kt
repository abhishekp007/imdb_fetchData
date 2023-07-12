package com.example.imdb_data

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.imdb_data.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    //https://www.omdbapi.com/?apikey=c0d64821&i=tt4154796

    lateinit var imdbCode:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener(View.OnClickListener {
//            binding.cardImdb.visibility = View.VISIBLE
//            binding.btnImdbSaveData.visibility =View.VISIBLE

            imdbCode = binding.txtImdbID.text.toString()

            if (imdbCode.isEmpty())
            {
                Toast.makeText(applicationContext,"Please Enter Id",Toast.LENGTH_LONG).show()
            }else
            {
                getData(imdbCode)
            }



        })



    }

    private fun getData(i:String) {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait While Data is fetch")
        progressDialog.show()

        RetrofitInstance.apiInterface.getData(i).enqueue(object : Callback<responceDataClass> {
            override fun onResponse(
                call: Call<responceDataClass>,
                response: Response<responceDataClass>
            ) {
                Glide.with(applicationContext).load(response.body()?.Poster).error(R.drawable.noimagefound).centerCrop().into(binding.imgImdbImg)

                binding.lblImdbRate.text = response.body()?.imdbRating
                binding.lblImdbTitle.text = response.body()?.Title
                binding.lblImdbDate.text = response.body()?.Released
                binding.lblImdbDirector.text = response.body()?.Director
                binding.lblImdbWriter.text = response.body()?.Writer
                binding.lblImdbStars.text = response.body()?.Actors
                binding.lblImdbSummary.text = response.body()?.Plot
                binding.lblImdbCountries.text = response.body()?.Country
                binding.lblImdbLanguages.text = response.body()?.Language

                progressDialog.dismiss()
                binding.cardImdb.visibility = View.VISIBLE
                binding.btnImdbSaveData.visibility =View.VISIBLE
                binding.btnImdbClearData.visibility =View.VISIBLE
            }

            override fun onFailure(call: Call<responceDataClass>, t: Throwable) {
                Toast.makeText(applicationContext,t.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                progressDialog.dismiss()

            }
        })
    }
}