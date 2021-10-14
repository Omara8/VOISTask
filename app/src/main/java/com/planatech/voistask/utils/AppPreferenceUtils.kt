package com.planatech.voistask.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object AppPreferenceUtils {

    private var sharedPreferences: SharedPreferences? = null

    fun initPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences("default_pref", MODE_PRIVATE)
    }

    fun getPreference(): SharedPreferences? {
        return sharedPreferences
    }

    fun setColorRGB(color: Int?) {
        color?.let {
            sharedPreferences?.edit()?.putInt(IMAGE_DOMINANT_COLOR, it)?.apply()
        }
    }

    fun getColorRGB(): Int? {
        return sharedPreferences?.getInt(IMAGE_DOMINANT_COLOR, 0)
    }
}