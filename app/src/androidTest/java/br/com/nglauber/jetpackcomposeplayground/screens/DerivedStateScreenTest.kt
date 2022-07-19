package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import org.junit.Test

class DerivedStateScreenTest : BaseTest() {
    @Test
    fun key_press_shows_correct_value() {
        val context = startApp {
            DerivedStateScreen()
        }
        val countValues = arrayOf(0, 1, 2, 3, 4, 5, 6)
        val derivedValues = arrayOf(0, 1, 4, 3, 8, 5, 12)
        val sideEffectValues = arrayOf(0, 0, 0, 3, 3, 3, 6)

        countValues.forEachIndexed { index, _ ->
            composeTestRule.onNodeWithText(
                context.getString(R.string.count_text, countValues[index])
            ).assertExists()
            composeTestRule.onNodeWithText(
                context.getString(R.string.msg_derived_count, derivedValues[index])
            ).assertExists()
            composeTestRule.onNodeWithText(
                context.getString(R.string.msg_side_effect_count, sideEffectValues[index])
            ).assertExists()
            composeTestRule.onNodeWithText(
                context.getString(R.string.btn_inc)
            ).performClick()
        }
    }
}