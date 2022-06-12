package com.jHerscu.clearskies.domain.repoInterface

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepo {

    suspend fun writeToStore(preference: Preferences.Key<Boolean>, option: Boolean)

    suspend fun readFromStore(key: Preferences.Key<Boolean>): Flow<Boolean>

}