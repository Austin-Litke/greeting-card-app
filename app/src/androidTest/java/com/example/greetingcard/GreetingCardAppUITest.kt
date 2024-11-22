package com.example.greetingcardapp

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class GreetingCardAppUITest {

    private lateinit var uiDevice: UiDevice
    private val packageName = "com.example.greetingcardapp" // Replace with your app's package name

    @Before
    fun setUp() {
        // Initialize UiDevice
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Press home to start from a clean state
        uiDevice.pressHome()
    }

    @Test
    fun testLaunchAppAndVerifySecondActivity() {
        // Launch the app using the package name
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear previous instances
        context.startActivity(intent)

        // Wait for the app to load
        uiDevice.waitForIdle()

        // Find and click the "start activity explicitly" button
        val startButton: UiObject = uiDevice.findObject(UiSelector().text("start activity explicitly"))
        try {
            startButton.click()
        } catch (e: UiObjectNotFoundException) {
            throw AssertionError("Start button not found")
        }

        // Verify that the second activity contains one of the challenges
        val challengeText = uiDevice.findObject(UiSelector().textContains("mobile software engineering challenge"))
        assertTrue("Challenge text not found!", challengeText.exists())
    }
}
