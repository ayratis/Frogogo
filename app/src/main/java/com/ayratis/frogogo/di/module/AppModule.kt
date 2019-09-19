package com.ayratis.frogogo.di.module

import android.content.Context
import com.ayratis.frogogo.BuildConfig
import com.ayratis.frogogo.data.Api
import com.ayratis.frogogo.di.BaseUrl
import com.ayratis.frogogo.di.provider.ApiProvider
import com.ayratis.frogogo.di.provider.OkHttpClientProvider
import com.ayratis.frogogo.system.*
import com.ayratis.frogogo.system.email_validation.EmailValidator
import com.ayratis.frogogo.system.email_validation.EmailValidatorProvider
import com.ayratis.frogogo.system.rx.AppSchedulers
import com.ayratis.frogogo.system.rx.SchedulersProvider
import com.google.gson.Gson
import okhttp3.OkHttpClient
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)
        bind(String::class.java).withName(BaseUrl::class.java).toInstance(BuildConfig.BASE_URL)
        bind(SchedulersProvider::class.java).toInstance(AppSchedulers())
        bind(Gson::class.java).toInstance(Gson())
        bind(ResourceManager::class.java).singletonInScope()

        //navigation
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)

        //network
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java).providesSingletonInScope()
        bind(Api::class.java).toProvider(ApiProvider::class.java).providesSingletonInScope()

        //utils
        bind(EmailValidatorProvider::class.java).toInstance(
            EmailValidator()
        )
    }
}