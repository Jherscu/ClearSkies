package com.jHerscu.clearskies.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val PREFERENCES = "preferences"

@Module
@InstallIn(SingletonComponent::class)
object PreferencesDataStoreModule {
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<androidx.datastore.preferences.core.Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {context.preferencesDataStoreFile(PREFERENCES)}
        )
    }
}