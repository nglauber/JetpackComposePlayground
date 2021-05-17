package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun DropDownScreen() {
    val countryList = listOf(
        "United States",
        "Australia",
        "Japan",
        "India",
    )
    CountrySelection(countryList)
}

@Composable
fun CountrySelection(items: List<String>, defaultValue: String = "") {
    val text = remember { mutableStateOf(defaultValue) }
    val isOpen = remember { mutableStateOf(false) }

    fun rotationValue(open: Boolean) = if (open) 180f else 0f
    val animRotation = remember {
        Animatable(initialValue = rotationValue(isOpen.value))
    }
    val coroutineScope = rememberCoroutineScope()
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        coroutineScope.launch {
            animRotation.animateTo(
                targetValue = rotationValue(isOpen.value),
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutLinearInEasing
                )
            )
        }
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = {
        text.value = it
    }
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        val componentColor = Color.Magenta
        Column {
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = {
                    Text(
                        text = "Country",
                        color = if (text.value == "") {
                            if (isSystemInDarkTheme()) Color.White else Color.Black
                        } else componentColor
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = componentColor
                ),
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .rotate(animRotation.value)
                    )
                }
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = items,
                openCloseOfDropDownList,
                userSelectedString
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp)
                .background(Color.Transparent)
                .clickable(
                    onClick = {
                        openCloseOfDropDownList(true)
                    }
                )
        )
    }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(
                    it, modifier = Modifier
                        .wrapContentWidth()
                )
            }
        }
    }
}