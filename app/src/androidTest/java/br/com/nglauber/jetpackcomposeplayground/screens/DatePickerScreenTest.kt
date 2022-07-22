package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.utils.BaseTest
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DatePickerScreenTest : BaseTest() {
    @Test
    fun navigate_to_months() {
        startApp {
            DatePickerScreen()
        }

        val calendar = Calendar.getInstance()

        var currentMonth = calendar.get(Calendar.MONTH)
        var currentYear = calendar.get(Calendar.YEAR)

        // Checking if it's displaying the current month / year
        composeTestRule.onNodeWithText(getDatePickerTitle(currentMonth, currentYear))
            .assertIsDisplayed()

        // Select previous month
        composeTestRule.onNodeWithTag(DatePickerPrevMonthButtonTestTag).performClick()
        calendar.add(Calendar.MONTH, -1)
        currentMonth = calendar.get(Calendar.MONTH)
        currentYear = calendar.get(Calendar.YEAR)

        // Checking if it's displaying the previous month / year
        isDisplayingTitleCorrectly(currentMonth, currentYear)

        // Select two months ahead
        composeTestRule.onNodeWithTag(DatePickerNextMonthButtonTestTag).performClick()
        composeTestRule.onNodeWithTag(DatePickerNextMonthButtonTestTag).performClick()
        calendar.add(Calendar.MONTH, 2)
        currentMonth = calendar.get(Calendar.MONTH)
        currentYear = calendar.get(Calendar.YEAR)

        // Checking if it's displaying the next month / year
        isDisplayingTitleCorrectly(currentMonth, currentYear)
    }

    @Test
    fun select_day_change_text() {
        startApp {
            DatePickerScreen()
        }
        val calendar = Calendar.getInstance()

        // Checking if it's displaying the current day
        selectedDateIsDisplayedCorrectly(calendar.time)

        // Changing the calendar day and pressing the day in the component
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        composeTestRule.onNodeWithText("1").performClick()

        // Checking if it's displaying the selected day
        selectedDateIsDisplayedCorrectly(calendar.time)
    }

    @Test
    fun change_month_and_change_the_day() {
        startApp {
            DatePickerScreen()
        }
        val calendar = Calendar.getInstance()

        // Checking if it's displaying the current day
        selectedDateIsDisplayedCorrectly(calendar.time)

        // Select previous month
        composeTestRule.onNodeWithTag(DatePickerPrevMonthButtonTestTag).performClick()
        calendar.add(Calendar.MONTH, -1)

        // Changing the calendar day and pressing the day in the component
        composeTestRule.onNodeWithText("1").performClick()
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        // Checking if it's displaying the selected day
        selectedDateIsDisplayedCorrectly(calendar.time)
    }

    private fun selectedDateIsDisplayedCorrectly(date: Date) {
        val formatter = SimpleDateFormat(DatePickerDateFormat, Locale.getDefault())
        composeTestRule.onNodeWithText(formatter.format(date)).assertIsDisplayed()
    }

    private fun isDisplayingTitleCorrectly(monthIndex: Int, year: Int) {
        composeTestRule.onNodeWithText(getDatePickerTitle(monthIndex, year))
            .assertIsDisplayed()
    }

    private fun getDatePickerTitle(monthIndex: Int, year: Int): String {
        val months = context.resources.getStringArray(R.array.months_abbreviation)
        return "${months[monthIndex]} - $year"
    }
}