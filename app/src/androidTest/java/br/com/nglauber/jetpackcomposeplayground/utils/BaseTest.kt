package br.com.nglauber.jetpackcomposeplayground.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import br.com.nglauber.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme
import org.junit.Rule

abstract class BaseTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    val context: Context
        get() = ApplicationProvider.getApplicationContext()

    fun startApp(
        content: @Composable () -> Unit
    ) {
        // Start the app
        composeTestRule.setContent {
            JetpackComposePlaygroundTheme {
                content()
            }
        }
    }
}