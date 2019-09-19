package com.ayratis.frogogo.di.provider

import com.ayratis.frogogo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider @Inject constructor() : Provider<OkHttpClient> {
    override fun get() = with(OkHttpClient.Builder()) {

        if (BuildConfig.DEBUG) {
            addNetworkInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }

        build()
    }
}