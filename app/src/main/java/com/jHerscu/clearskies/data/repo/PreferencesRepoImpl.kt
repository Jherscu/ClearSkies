package com.jHerscu.clearskies.data.repo

import androidx.datastore.preferences.core.Preferences
import com.jHerscu.clearskies.domain.repoInterface.PreferencesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PreferencesRepoImpl : PreferencesRepo {

    override suspend fun writeToStore(preference: Preferences.Key<Boolean>, option: Boolean) {
    }

    override suspend fun readFromStore(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return flowOf(true) // TODO(jherscu): Do something here
    }
}
