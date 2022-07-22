package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.unit.Dp
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import org.junit.Test

class ConstraintLayoutScreenTest : BaseTest() {
    @Test
    fun copy_paste_action() {
        startApp {
            ConstraintLayoutScreen()
        }
        val input = "Jetpack Compose"
        composeTestRule.onNodeWithText(context.getString(R.string.msg_name))
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag(ConstraintLayoutScreenPastedTextTestTag)
            .assertTextEquals("")

        composeTestRule.onNodeWithText(context.getString(R.string.msg_name_hint))
            .assertIsDisplayed()
            .performTextInput(input)

        composeTestRule.onNodeWithText(context.getString(R.string.btn_copy))
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithText(context.getString(R.string.btn_paste))
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag(ConstraintLayoutScreenPastedTextTestTag)
            .assertTextEquals(input)
    }

    @Test
    fun weight_size_is_correct() {
        startApp {
            ConstraintLayoutScreen()
        }
        var w = 0
        composeTestRule.onRoot().performTouchInput {
            w = width
        }

        listOf(
            "20%" to 0.2f,
            "50%" to 0.5f,
            "30%" to 0.3f,
        ).forEach {
            val (text, percentage) = it
            val expected = w * percentage / composeTestRule.density.density
            composeTestRule.onNodeWithText(text).assertWidthIsEqualTo(Dp(expected))
        }
    }
}