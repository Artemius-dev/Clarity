package org.artemzhuravlov.clarity.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

actual val platformModule: Module
    get() = module {
        single<Settings.Factory> {
            SharedPreferencesSettings.Factory(androidContext())
        }
    }