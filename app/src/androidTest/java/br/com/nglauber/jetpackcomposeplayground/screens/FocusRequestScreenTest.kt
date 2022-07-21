package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import org.junit.Test

@ExperimentalFoundationApi
class FocusRequestScreenTest : BaseTest() {
    private val prefix = "Focus Transition Text"

    @Test
    fun next_focus_is_focused() {
        startApp {
            FocusRequestScreen()
        }
        (0..8).forEach { index ->
            val current = "$prefix $index"
            if (index == 0) {
                composeTestRule.onNodeWithText(current)
                    .assertIsDisplayed()
                    .performClick()
            }
            composeTestRule.onNodeWithText(current)
                .assertIsDisplayed()
                .assertIsFocused()
                .performImeAction()
        }
        composeTestRule.onNodeWithText("$prefix 0")
            .assertIsFocused()
    }

    @Test
    fun tapping_on_last_field_make_it_visible() {
        startApp {
            FocusRequestScreen()
        }
        composeTestRule.onNodeWithText("$prefix 8")
            .performClick()
            .assertIsDisplayed()
            .assertIsFocused()
    }
}