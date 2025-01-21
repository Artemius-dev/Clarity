package org.artemzhuravlov.clarity

import android.app.Application
import android.content.Context
import org.artemzhuravlov.clarity.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApplication)
        }
    }
}