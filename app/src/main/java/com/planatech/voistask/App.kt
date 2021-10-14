package com.planatech.voistask

import android.app.Application
import com.planatech.voistask.utils.AppPreferenceUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferenceUtils.initPreferences(this)
    }
}