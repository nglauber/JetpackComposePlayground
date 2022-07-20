package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import org.junit.Test

class DropDownScreenTest : BaseTest() {
    @Test
    fun drop_down_value_is_selected() {
        startApp {
            DropDownScreen()
        }
        val optionToSelect = "Japan"
        // Assert that there's no component with "Japan" text
        composeTestRule.onNodeWithText(optionToSelect).assertDoesNotExist()
        // Now, touch on the DropDown
        composeTestRule.onNodeWithTag(DropDownTestTag).performClick()
        // Check if the drop down list is displayed
        composeTestRule.onNodeWithTag(DropDownListTestTag).assertIsDisplayed()
        // Select the option in the list
        composeTestRule.onNodeWithText(optionToSelect).performClick()
        // Check if the drop down list was removed
        composeTestRule.onNodeWithTag(DropDownListTestTag).assertDoesNotExist()
        // Check if the selection is being displayed
        composeTestRule.onNodeWithText(optionToSelect).assertIsDisplayed()
    }
}