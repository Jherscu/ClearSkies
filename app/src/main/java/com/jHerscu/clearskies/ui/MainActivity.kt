package com.jHerscu.clearskies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jHerscu.clearskies.BuildConfig
import com.jHerscu.clearskies.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}