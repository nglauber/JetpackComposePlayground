package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import org.junit.Test

class KeyboardScreenTest : BaseTest() {

    @Test
    fun key_press_shows_the_key_elevated() {
        startApp {
            KeyboardScreen()
        }
        val keyToTest = "Q"
        val elevatedKeyTestTag = "$keyToTest$KeyboardScreenTestTagPressedSuffix"
        // Before pressed, the elevated key does not exists
        composeTestRule.onNodeWithTag(elevatedKeyTestTag).assertDoesNotExist()
        // Press the key to display the elevated key
        composeTestRule
            .onNodeWithTag(keyToTest)
            .performTouchInput {
                down(Offset.Zero)
            }
        // Assert the elevated key is displayed
        composeTestRule.onNodeWithTag(elevatedKeyTestTag).assertExists()
        // Releasing the pressed key
        composeTestRule
            .onNodeWithTag(keyToTest)
            .performTouchInput {
                up()
            }
        // Assert the elevated key no longer exists
        composeTestRule.onNodeWithTag(elevatedKeyTestTag).assertDoesNotExist()
    }

    @Test
    fun key_pressed_is_displayed() {
        val context = startApp {
            KeyboardScreen()
        }
        val keyToTest = "G"

        val startText = context.getString(R.string.msg_last_key_pressed, "")
        val endText = context.getString(R.string.msg_last_key_pressed, keyToTest)
        composeTestRule.onNodeWithText(startText).assertExists()
        composeTestRule.onNodeWithText(keyToTest).performClick()
        composeTestRule.onNodeWithText(endText).assertExists()
    }
}