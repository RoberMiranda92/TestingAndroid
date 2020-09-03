package com.cornershop.example

import android.app.Application

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.context = this.applicationContext
    }
}