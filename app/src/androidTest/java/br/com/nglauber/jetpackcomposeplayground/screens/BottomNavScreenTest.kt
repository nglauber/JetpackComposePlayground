package br.com.nglauber.jetpackcomposeplayground.screens

import android.content.Context
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
        val context = startApp {
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
        val context = startApp {
            BottomNavScreen()
        }
        // Check Tab1Main is displayed
        tab1MainIsDisplayed(context)

        // Click on 'Next' to show the details
        openTab1Details(context)

        // The first tab has these components
        composeTestRule.onNodeWithText(context.getString(R.string.msg_email)).assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.btn_next)).assertDoesNotExist()

        // The details of first tab has these components
        tab1DetailsIsDisplayed(context)

        // Pressing back to show Tab1Main
        Espresso.pressBack()

        // Tab1Main is displayed again
        tab1MainIsDisplayed(context)
    }

    @Test
    fun navigation_in_second_tab() {
        val context = startApp {
            BottomNavScreen()
        }
        // Select second tab
        selectTab2(context)

        composeTestRule.waitForIdle()

        // Tab2 Main has this button
        tab2MainIsDisplayed(context)

        openTab2Details(context)

        // Tab2 Details
        tab2DetailsIsDisplayed(context)

        // Pressing back to show Tab2Main
        Espresso.pressBack()

        // Tab2 Main has this button
        tab2MainIsDisplayed(context)
    }

    @Test
    fun navigation_in_both_tabs_and_check_back_stack() {
        val context = startApp {
            BottomNavScreen()
        }
        tab1MainIsDisplayed(context)
        openTab1Details(context)
        tab1DetailsIsDisplayed(context)

        selectTab2(context)

        tab2MainIsDisplayed(context)
        openTab2Details(context)
        tab2DetailsIsDisplayed(context)

        Espresso.pressBack()
        tab2MainIsDisplayed(context)
        Espresso.pressBack()
        tab1DetailsIsDisplayed(context)
        Espresso.pressBack()
        tab1MainIsDisplayed(context)
    }

    private fun tab1MainIsDisplayed(context: Context) {
        // The first tab has these components
        composeTestRule.onNodeWithText(context.getString(R.string.msg_email))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.btn_next)).assertIsDisplayed()
        // The details of first tab has these components
        composeTestRule.onNodeWithText(context.getString(R.string.tab_1_details))
            .assertDoesNotExist()
    }

    private fun openTab1Details(context: Context) {
        composeTestRule.onNodeWithText(context.getString(R.string.btn_next)).performClick()
    }

    private fun tab1DetailsIsDisplayed(context: Context) {
        composeTestRule.onNodeWithText(context.getString(R.string.tab_1_details))
            .assertIsDisplayed()
    }

    private fun tab2MainIsDisplayed(context: Context) {
        composeTestRule.onNodeWithText(context.getString(R.string.btn_tab_2_details))
            .assertIsDisplayed()
    }

    private fun tab2DetailsIsDisplayed(context: Context) {
        composeTestRule.onNodeWithText(context.getString(R.string.tab_2_details))
            .assertIsDisplayed()
    }

    private fun openTab2Details(context: Context) {
        composeTestRule.onNodeWithText(context.getString(R.string.btn_tab_2_details))
            .performClick()
    }

    private fun selectTab2(context: Context) {
        composeTestRule.onNodeWithText(context.getString(R.string.tab_2)).performClick()
    }
}