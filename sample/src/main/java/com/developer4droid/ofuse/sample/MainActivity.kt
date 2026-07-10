package com.developer4droid.ofuse.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.developer4droid.ofuse.SupportDeveloperRow
import com.developer4droid.ofuse.sample.ui.theme.OfuseTheme

// Replace with your own Ko-fi page before shipping.
private const val SAMPLE_KOFI_URL = "https://ko-fi.com/developer4droid"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OfuseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        SupportDeveloperRow(supportUrl = SAMPLE_KOFI_URL)
                    }
                }
            }
        }
    }
}
