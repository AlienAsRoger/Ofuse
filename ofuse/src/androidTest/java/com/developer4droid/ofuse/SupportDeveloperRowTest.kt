package com.developer4droid.ofuse

import android.content.Intent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented Compose UI tests for [SupportDeveloperRow], covering both the
 * content it renders and the Custom Tab launch it triggers on tap.
 */
@RunWith(AndroidJUnit4::class)
class SupportDeveloperRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun defaultTitleAndDescriptionAreDisplayed() {
        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    SupportDeveloperRow(supportUrl = "https://ko-fi.com/developer4droid")
                }
            }
        }

        composeTestRule.onNodeWithText("Support development").assertExists()
        composeTestRule.onNodeWithText("Buy me a coffee").assertExists()
    }

    @Test
    fun customTitleAndDescriptionAreDisplayed() {
        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    SupportDeveloperRow(
                        supportUrl = "https://ko-fi.com/developer4droid",
                        title = "Custom title",
                        description = "Custom description",
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Custom title").assertExists()
        composeTestRule.onNodeWithText("Custom description").assertExists()
    }

    @Test
    fun nullDescriptionIsNotDisplayed() {
        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    SupportDeveloperRow(
                        supportUrl = "https://ko-fi.com/developer4droid",
                        description = null,
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Buy me a coffee").assertDoesNotExist()
    }

    @Test
    fun iconIsDisplayedWhenProvided() {
        val fakeIcon: ImageVector = ImageVector.Builder(
            name = "fake",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path { moveTo(0f, 0f); lineTo(24f, 0f); lineTo(24f, 24f); close() }
        }.build()

        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    SupportDeveloperRow(
                        supportUrl = "https://ko-fi.com/developer4droid",
                        icon = fakeIcon,
                    )
                }
            }
        }

        // Regression test: passing a non-null icon must not crash the leadingContent slot,
        // and the rest of the row should still render normally.
        composeTestRule.onNodeWithText("Support development").assertExists()
    }

    @Test
    fun tappingRowLaunchesCustomTabForSupportUrl() {
        val supportUrl = "https://ko-fi.com/developer4droid"
        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    SupportDeveloperRow(supportUrl = supportUrl)
                }
            }
        }

        composeTestRule.onNodeWithText("Support development").performClick()

        intended(hasAction(Intent.ACTION_VIEW))
        intended(hasData(supportUrl))
    }
}
