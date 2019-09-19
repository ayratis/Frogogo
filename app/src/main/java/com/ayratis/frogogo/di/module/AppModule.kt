package com.ayratis.frogogo.di.module

import android.content.Context
import com.ayratis.frogogo.BuildConfig
import com.ayratis.frogogo.di.BaseUrl
import com.ayratis.frogogo.system.AppSchedulers
import com.ayratis.frogogo.system.SchedulersProvider
import com.google.gson.Gson
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

        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
    }
}