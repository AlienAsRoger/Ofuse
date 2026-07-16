package com.developer4droid.ofuse

import android.content.Intent
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

/**
 * Unit tests for [OfuseLauncher]'s public API, run on the JVM via Robolectric
 * (no emulator/device required).
 */
@RunWith(RobolectricTestRunner::class)
class OfuseLauncherTest {

    @Test
    fun `openSupportLink starts an activity pointing at the given url`() {
        val context = ApplicationProvider.getApplicationContext<android.app.Application>()
        val url = "https://ko-fi.com/developer4droid"

        OfuseLauncher.openSupportLink(context, url)

        val startedIntent: Intent? = shadowOf(context).nextStartedActivity
        assertTrue("Expected an activity to have been started", startedIntent != null)
        assertEquals(Uri.parse(url), startedIntent!!.data)
    }

    @Test
    fun `openSupportLink adds the new task flag for non-activity contexts`() {
        val context = ApplicationProvider.getApplicationContext<android.app.Application>()
        val url = "https://ko-fi.com/developer4droid"

        OfuseLauncher.openSupportLink(context, url)

        val startedIntent: Intent? = shadowOf(context).nextStartedActivity
        assertTrue(
            "Expected FLAG_ACTIVITY_NEW_TASK to be set when launching from a non-Activity context",
            startedIntent!!.flags and Intent.FLAG_ACTIVITY_NEW_TASK != 0
        )
    }

    @Test
    fun `openSupportLink works for arbitrary https urls`() {
        val context = ApplicationProvider.getApplicationContext<android.app.Application>()
        val url = "https://buymeacoffee.com/someone"

        OfuseLauncher.openSupportLink(context, url)

        val startedIntent: Intent? = shadowOf(context).nextStartedActivity
        assertEquals(Uri.parse(url), startedIntent!!.data)
    }
}
