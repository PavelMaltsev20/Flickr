package com.pavelmaltsev.flickrgallery.data.retrofit

import com.pavelmaltsev.flickrgallery.constants.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val getRetrofitService: RetrofitService by lazy {
        getRetrofitBuilder.create(RetrofitService::class.java)
    }

    private val getRetrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
