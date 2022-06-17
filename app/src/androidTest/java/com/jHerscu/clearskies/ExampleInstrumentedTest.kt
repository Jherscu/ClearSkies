package com.jHerscu.clearskies

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jHerscu.clearskies.di.DbModule
import com.jHerscu.clearskies.di.DispatcherModule
import com.jHerscu.clearskies.di.NetworkModule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@UninstallModules(
    DbModule::class,
    DispatcherModule::class,
    NetworkModule::class
)
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jHerscu.clearskies", appContext.packageName)
    }
}