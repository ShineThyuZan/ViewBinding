package com.galaxy_techno.buyer

import androidx.multidex.MultiDexApplication
import com.bumptech.glide.annotation.GlideModule
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@GlideModule
@HiltAndroidApp
class BuyerApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}