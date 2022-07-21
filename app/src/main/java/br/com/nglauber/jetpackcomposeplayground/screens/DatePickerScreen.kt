package br.com.nglauber.jetpackcomposeplayground.screens

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.nglauber.jetpackcomposeplayground.R
import java.util.*

@Composable
fun DatePickerScreen() {
    val formatter = remember(Unit) {
        SimpleDateFormat(DatePickerDateFormat, Locale.getDefault())
    }
    var currentDate by remember {
        mutableStateOf(Date())
    }
    val currentDateStr by remember {
        derivedStateOf {
            formatter.format(currentDate).toString()
        }
    }
    Column {
        DatePicker(
            selectedDate = currentDate,
            onDateSelected = {
                currentDate = it
            }
        )
        Text(text = currentDateStr)
    }
}

fun getSelectedDay(
    selectedDate: Date,
    calendarDate: Date,
): Int? {
    val calendar = Calendar.getInstance().apply {
        time = selectedDate
    }
    val selectedYear = calendar[Calendar.YEAR]
    val selectedMonth = calendar[Calendar.MONTH]
    val calendar2 = Calendar.getInstance().apply {
        time = calendarDate
    }
    val calendarYear = calendar2[Calendar.YEAR]
    val calendarMonth = calendar2[Calendar.MONTH]
    return if (selectedYear == calendarYear && selectedMonth == calendarMonth) {
        calendar[Calendar.DAY_OF_MONTH]
    } else null
}

@Composable
fun DatePicker(
    selectedDate: Date = Date(),
    onDateSelected: (Date) -> Unit,
) {
    val monthNames = stringArrayResource(id = R.array.months_abbreviation)
    var currentMonth by remember {
        mutableStateOf(selectedDate)
    }
    val calendar = Calendar.getInstance().apply {
        time = currentMonth
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val monthName = "${monthNames[calendar[Calendar.MONTH]]} - ${calendar[Calendar.YEAR]}"
    val firstDayOfMonthWeekDay = calendar[Calendar.DAY_OF_WEEK] - 1
    val lastDayOfTheMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val selectedDay = getSelectedDay(selectedDate, calendar.time)
    DatePickerImpl(
        headerTitle = monthName,
        firstDayOfMonthWeekDay = firstDayOfMonthWeekDay,
        lastDayOfTheMonth = lastDayOfTheMonth,
        selectedDay = selectedDay,
        onPreviousMonthPressed = {
            calendar.add(Calendar.MONTH, -1)
            currentMonth = calendar.time
        },
        onNextMonthPressed = {
            calendar.add(Calendar.MONTH, 1)
            currentMonth = calendar.time
        },
        onDaySelected = {
            val cal = Calendar.getInstance().apply {
                time = currentMonth
                set(Calendar.DAY_OF_MONTH, it)
            }
            onDateSelected(cal.time)
        }
    )
}

@Composable
private fun DatePickerImpl(
    headerTitle: String,
    selectedDay: Int?,
    firstDayOfMonthWeekDay: Int,
    lastDayOfTheMonth: Int,
    onPreviousMonthPressed: () -> Unit,
    onNextMonthPressed: () -> Unit,
    onDaySelected: (Int) -> Unit
) {
    val weekDayNames = stringArrayResource(R.array.days_of_week)
    var dayCounter = 1
    Column(Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onPreviousMonthPressed,
                modifier = Modifier.testTag(DatePickerPrevMonthButtonTestTag)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
            Text(text = headerTitle, Modifier.weight(1f), textAlign = TextAlign.Center)
            IconButton(
                onClick = onNextMonthPressed,
                modifier = Modifier.testTag(DatePickerNextMonthButtonTestTag)
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            weekDayNames.forEach {
                Box(
                    Modifier
                        .weight(1f)
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = it.substring(0, 3))
                }
            }
        }
        var initWeekday = firstDayOfMonthWeekDay
        while (dayCounter <= lastDayOfTheMonth) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
            ) {
                if (initWeekday > 0) {
                    repeat(initWeekday) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                for (i in 1..(7 - initWeekday)) {
                    if (dayCounter <= 31) {
                        val dayOfMonth = dayCounter
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clip(CircleShape)
                                .background(
                                    if (selectedDay == dayCounter) Color.DarkGray else Color.Magenta,
                                    CircleShape
                                )
                                .clickable {
                                    onDaySelected(dayOfMonth)
                                }
                                .padding(10.dp)
                        ) {
                            Text(text = dayCounter++.toString(), color = Color.White)
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                initWeekday = 0
            }
        }
    }
}

const val DatePickerDateFormat = "EEE MMM dd, yyyy"
const val DatePickerPrevMonthButtonTestTag = "pickerPrevMonth"
const val DatePickerNextMonthButtonTestTag = "pickerNextMonth"