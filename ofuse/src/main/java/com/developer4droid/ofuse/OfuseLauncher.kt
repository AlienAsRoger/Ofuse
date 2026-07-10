package com.developer4droid.ofuse

import android.content.Context
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
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(context, Uri.parse(url))
    }
}
