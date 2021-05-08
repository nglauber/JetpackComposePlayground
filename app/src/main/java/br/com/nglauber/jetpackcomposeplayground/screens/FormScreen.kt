package br.com.nglauber.jetpackcomposeplayground.screens

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.databinding.MyBindingLayoutBinding
import java.util.*
import kotlin.math.roundToInt

@Composable
fun FormScreen() {
    val focusManager = LocalFocusManager.current
    var nameState by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(true) }
    var isOn by remember { mutableStateOf(true) }
    var sliderValue by remember { mutableStateOf(0f) }

    val options = listOf(
        "Opção 1", "Opção 2", "Opção 3"
    )
    var currentOption by remember { mutableStateOf("Opção 1") }

    var date by remember { mutableStateOf<Date?>(null) }

    Column(Modifier.verticalScroll(rememberScrollState())) {
        TextField(
            value = nameState,
            label = { Text("Digite seu nome") },
            onValueChange = { s: String ->
                nameState = s
            },
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it }
        )

        Switch(
            checked = isOn,
            onCheckedChange = { isOn = it }
        )

        Text("Slider: ${sliderValue.roundToInt()}")
        Slider(value = sliderValue, onValueChange = { sliderValue = it }, valueRange = 0f..100f)

        SegmentedControl()

        Chips()

        if (isChecked) {
            RadioOptions(
                options = options,
                currentOption = currentOption,
                onChange = { currentOption = it })
        }
        if (date != null) {
            Text(date.toString())
        }
        MyCalendar(date) { date = it }
        BindingView()
    }
}

@Composable
fun RadioOptions(
    options: List<String>,
    currentOption: String,
    onChange: (String) -> Unit
) {
    options.forEach { text ->
        Row(
            Modifier.fillMaxWidth()
        ) {
            RadioButton(
                selected = (text == currentOption),
                onClick = { onChange(text) }
            )
            Text(text = text)
        }
    }
}

@SuppressLint("InflateParams")
@Composable
fun MyCalendar(current: Date?, onDateUpdate: (Date) -> Unit) {
    AndroidView(
        factory = { context: Context ->
            val view =
                LayoutInflater.from(context).inflate(R.layout.layout_calendar, null, false)

            val textView = view.findViewById<TextView>(R.id.txtDate)
            val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
            val button = view.findViewById<Button>(R.id.button)
            calendarView?.setOnDateChangeListener { cv, year, month, day ->
                val date = Calendar.getInstance().apply {
                    set(year, month, day)
                }.time
                textView?.text = date.toString()
                onDateUpdate(date)
            }
            current?.let {
                calendarView.date = it.time
            }
            button.setOnClickListener {
                Toast.makeText(context, "Hello!", Toast.LENGTH_LONG).show()
            }
            view
        },
        update = { view ->
            // Update view
        }
    )
}

@Composable
fun SegmentedControl() {
    var toggleIndex by remember { mutableStateOf(1) }
    Row(modifier = Modifier.fillMaxWidth()) {
        (1 until 5).forEach { index ->
            val isSelected = toggleIndex == index
            Box(modifier = Modifier
                .border(1.dp, color = Color.Black, CircleShape)
                .background(
                    color = if (isSelected) Color.Black else Color.White,
                    shape = CircleShape,
                )
                .selectable(
                    selected = isSelected,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, radius = 24.dp),
                    onClick = { toggleIndex = index },
                )
                .size(48.dp)
            ) {
                Text(
                    text = index.toString(),
                    style = TextStyle(
                        color = if (isSelected) Color.White else Color.Black,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

@Composable
fun BindingView() {
    AndroidViewBinding(factory = MyBindingLayoutBinding::inflate) {
        var count = 0
        txtTitle.text = "Count: $count"
        btnOk.setOnClickListener {
            count++
            txtTitle.text = "Count: $count"
        }
    }
}