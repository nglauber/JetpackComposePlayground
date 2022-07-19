package br.com.nglauber.jetpackcomposeplayground.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import br.com.nglauber.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme
import org.junit.Rule

abstract class BaseTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    fun startApp(
        content: @Composable () -> Unit
    ): Context {
        var context: Context? = null
        // Start the app
        composeTestRule.setContent {
            JetpackComposePlaygroundTheme {
                context = LocalContext.current
                content()
            }
        }
        return context!!
    }
}