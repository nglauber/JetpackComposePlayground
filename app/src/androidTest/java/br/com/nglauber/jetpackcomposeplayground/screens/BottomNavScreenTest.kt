package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.bottomnav.BottomNavScreen
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import org.junit.Test

class BottomNavScreenTest : BaseTest() {

    @Test
    fun validate_email() {
        startApp {
            BottomNavScreen()
        }
        composeTestRule.onNodeWithText(context.getString(R.string.msg_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.msg_email))
            .performTextInput("bla")
        composeTestRule.onNodeWithText(context.getString(R.string.msg_invalid_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.msg_email))
            .performTextInput("nelson@test.com")
        composeTestRule.onNodeWithText(context.getString(R.string.msg_invalid_email))
            .assertDoesNotExist()
    }

    @Test
    fun navigation_in_first_tab() {
        startApp {
            BottomNavScreen()
        }
        // Check Tab1Main is displayed
        tab1MainIsDisplayed()

        // Click on 'Next' to show the details
        openTab1Details()

        // The first tab has these components
        composeTestRule.onNodeWithText(context.getString(R.string.msg_email)).assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.btn_next)).assertDoesNotExist()

        // The details of first tab has these components
        tab1DetailsIsDisplayed()

        // Pressing back to show Tab1Main
        Espresso.pressBack()

        // Tab1Main is displayed again
        tab1MainIsDisplayed()
    }

    @Test
    fun navigation_in_second_tab() {
        startApp {
            BottomNavScreen()
        }
        // Select second tab
        selectTab2()

        composeTestRule.waitForIdle()

        // Tab2 Main has this button
        tab2MainIsDisplayed()

        openTab2Details()

        // Tab2 Details
        tab2DetailsIsDisplayed()

        // Pressing back to show Tab2Main
        Espresso.pressBack()

        // Tab2 Main has this button
        tab2MainIsDisplayed()
    }

    @Test
    fun navigation_in_both_tabs_and_check_back_stack() {
        startApp {
            BottomNavScreen()
        }
        tab1MainIsDisplayed()
        openTab1Details()
        tab1DetailsIsDisplayed()

        selectTab2()

        tab2MainIsDisplayed()
        openTab2Details()
        tab2DetailsIsDisplayed()

        Espresso.pressBack()
        tab2MainIsDisplayed()
        Espresso.pressBack()
        tab1DetailsIsDisplayed()
        Espresso.pressBack()
        tab1MainIsDisplayed()
    }

    private fun tab1MainIsDisplayed() {
        // The first tab has these components
        composeTestRule.onNodeWithText(context.getString(R.string.msg_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.btn_next)).assertIsDisplayed()
        // The details of first tab has these components
        composeTestRule.onNodeWithText(context.getString(R.string.tab_1_details))
            .assertDoesNotExist()
    }

    private fun openTab1Details() {
        composeTestRule.onNodeWithText(context.getString(R.string.btn_next)).performClick()
    }

    private fun tab1DetailsIsDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.tab_1_details))
            .assertIsDisplayed()
    }

    private fun tab2MainIsDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.btn_tab_2_details))
            .assertIsDisplayed()
    }

    private fun tab2DetailsIsDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.tab_2_details))
            .assertIsDisplayed()
    }

    private fun openTab2Details() {
        composeTestRule.onNodeWithText(context.getString(R.string.btn_tab_2_details))
            .performClick()
    }

    private fun selectTab2() {
        composeTestRule.onNodeWithText(context.getString(R.string.tab_2)).performClick()
    }
}