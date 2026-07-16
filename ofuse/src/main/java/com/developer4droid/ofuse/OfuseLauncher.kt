package com.developer4droid.ofuse

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Opens an external support/donation link in a Custom Tab.
 *
 * Exposed standalone for consumers who want to trigger it from their own UI
 * instead of [SupportDeveloperRow].
 */
object OfuseLauncher {
    fun openSupportLink(context: Context, url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        // Non-Activity contexts (Application, Service, ...) require this flag or
        // startActivity() throws -- Activity contexts already have a task to launch into.
        if (context !is Activity) {
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}
