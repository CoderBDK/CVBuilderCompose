package com.coderbdk.cvbuilder

import android.app.Application
import com.coderbdk.cvbuilder.di.initKoin

class CVApplication: Application() {
    companion object {
        lateinit var AppContext: Application
    }

    override fun onCreate() {
        super.onCreate()
        AppContext = this
        initKoin()
    }
}