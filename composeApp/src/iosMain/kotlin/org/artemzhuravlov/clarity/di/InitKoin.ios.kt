package org.artemzhuravlov.clarity.di

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalSettingsImplementation::class)
actual val platformModule: Module
    get() = module {
        single<Settings.Factory> {
            KeychainSettings.Factory()
        }
    }