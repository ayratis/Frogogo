package com.ayratis.frogogo.di.provider

import com.ayratis.frogogo.data.Api
import com.ayratis.frogogo.di.BaseUrl
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ApiProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private  val gson: Gson,
    @BaseUrl private val baseurl: String
) : Provider<Api>{
    override fun get() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .baseUrl(baseurl)
        .build()
        .create(Api::class.java)
}