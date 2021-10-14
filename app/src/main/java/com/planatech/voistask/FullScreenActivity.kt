package com.planatech.voistask

import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.planatech.voistask.databinding.ActivityFullScreenBinding
import com.planatech.voistask.utils.AppPreferenceUtils
import com.planatech.voistask.utils.IMAGE_DOMINANT_COLOR
import com.planatech.voistask.utils.IMAGE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullScreenActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private var sharedPreferenceListener: SharedPreferences.OnSharedPreferenceChangeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val imageURL = intent.extras?.getString(IMAGE_URL)
        binding.imageUrl = imageURL

        sharedPreferences = AppPreferenceUtils.getPreference()

        sharedPreferenceListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                IMAGE_DOMINANT_COLOR -> {
                    AppPreferenceUtils.getColorRGB()?.let {
                        binding.fullLayout.setBackgroundColor(it)
                    }
                }
            }
        }
    }

    override fun onResume() {
        sharedPreferences?.registerOnSharedPreferenceChangeListener(sharedPreferenceListener)
        super.onResume()
    }

    override fun onPause() {
        sharedPreferences?.unregisterOnSharedPreferenceChangeListener(sharedPreferenceListener)
        sharedPreferences?.edit()?.clear()?.apply()
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}