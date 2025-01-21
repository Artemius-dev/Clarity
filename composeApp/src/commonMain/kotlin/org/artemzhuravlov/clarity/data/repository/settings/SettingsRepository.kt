package org.artemzhuravlov.clarity.data.repository.settings

import com.russhwolf.settings.Settings

private const val SETTINGS_NAME = "clarity_settings"

class SettingsRepository(factory: Settings.Factory) {
    private val settings = factory.create(SETTINGS_NAME)

    fun clear() = settings.clear()
}