package com.example.apiservicesmodule

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplicationServices : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}