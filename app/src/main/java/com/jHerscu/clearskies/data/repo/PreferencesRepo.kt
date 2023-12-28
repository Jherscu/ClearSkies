package com.jHerscu.clearskies.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepo
    @Inject
    constructor(
        private val preferencesDataStore: DataStore<Preferences>,
    ) {
        suspend fun writeToStore(
            preference: Preferences.Key<Boolean>,
            option: Boolean,
        ) {
            preferencesDataStore.edit { it[preference] = option }
        }

        suspend fun writeToStore(
            preference: Preferences.Key<String>,
            option: String,
        ) {
            preferencesDataStore.edit { it[preference] = option }
        }

        fun readFromStore(): Flow<Preferences> = preferencesDataStore.data

        companion object Keys {
            val TWENTY_FOUR_HOUR_MODE_KEY = booleanPreferencesKey("24_hour_mode")
            val DYNAMIC_THEMING_KEY = booleanPreferencesKey("dynamic_theming")
            val TEMP_UNIT_KEY = stringPreferencesKey("temp_unit")
            val LOCKED_THEME_KEY = stringPreferencesKey("locked_theme")
            val HOME_DISPLAY_INTERVAL_KEY = stringPreferencesKey("home_display_interval")
            val PREF_ORDER_COMPARATOR_KEY = stringPreferencesKey("pref_order_comparator")
        }
    }
