package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith



//const val SETTINGS_PACKAGE = "com.android.settings"
//const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"
//
//const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    private lateinit var device: UiDevice
    private val textToSet = " "
    private val textToSetWithNewActivity = "123"
    private val nonChangedText = "Hello UiAutomator!"

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }


    @After
    fun tearDown() {
    }

    @Test
    fun blankString() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName, "userInput")).text = textToSet
        device.findObject(By.res(packageName, "buttonChange")).click()

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(result, nonChangedText)
    }

    @Test
    fun textInActivity() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName, "userInput")).text = textToSetWithNewActivity
        device.findObject(By.res(packageName, "buttonActivity")).click()
        device.waitForWindowUpdate(MODEL_PACKAGE,1000)
        val result = device.findObject(By.res(packageName, "text")).text
        assertEquals(result, textToSetWithNewActivity)
    }

//    Тест на попытку установки пустой строки.
//    Тест на открытие текста в новой Activity.

}