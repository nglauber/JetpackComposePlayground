package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.pressKey
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import org.junit.Test

@ExperimentalTestApi
@ExperimentalComposeUiApi
class NumberPadScreenTest : BaseTest() {

    @Test
    fun focus_and_values_are_correct() {
        startApp {
            NumberPadScreen()
        }
        val inputs = (0..4).map {
            composeTestRule.onNodeWithTag(
                "${NumberPadScreenTestTagPrefix}$it"
            )
        }

        val firstInput = inputs[0]
        firstInput.assertIsDisplayed()
        firstInput.performClick()
        firstInput.assertIsFocused()
        firstInput.performTextInput("5")

        val secondInput = inputs[1]
        secondInput.assertIsDisplayed()
        secondInput.assertIsFocused()
        secondInput.performTextInput("4")

        val thirdInput = inputs[2]
        thirdInput.assertIsDisplayed()
        thirdInput.assertIsFocused()
        thirdInput.performTextInput("3")

        val fourthInput = inputs[3]
        fourthInput.assertIsDisplayed()
        fourthInput.assertIsFocused()
        fourthInput.performTextInput("2")

        val fifthInput = inputs[4]
        fifthInput.assertIsDisplayed()
        fifthInput.assertIsFocused()
        fifthInput.performTextInput("1")

        fifthInput.performKeyInput {
            pressKey(Key.Backspace)
        }
        fourthInput.assertIsFocused()
        fourthInput.performKeyInput {
            pressKey(Key.Backspace)
        }
        thirdInput.assertIsFocused()
        thirdInput.performKeyInput {
            pressKey(Key.Backspace)
        }
        secondInput.assertIsFocused()
        secondInput.performKeyInput {
            pressKey(Key.Backspace)
        }
    }
}