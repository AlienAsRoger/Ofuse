package com.developer4droid.ofuse

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext

/**
 * A ready-made settings-style row for a "support the developer" entry point.
 *
 * Drop it into any Column/LazyColumn inside your own MaterialTheme; tapping
 * it opens [supportUrl] in a Custom Tab (no in-app purchase involved).
 */
@Composable
fun SupportDeveloperRow(
    supportUrl: String,
    modifier: Modifier = Modifier,
    title: String = "Support development",
    description: String? = "Buy me a coffee",
    icon: ImageVector? = null,
) {
    val context = LocalContext.current
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = description?.let { { Text(it) } },
        leadingContent = icon?.let { { Icon(it, contentDescription = null) } },
        modifier = modifier.clickable {
            OfuseLauncher.openSupportLink(context, supportUrl)
        }
    )
}
