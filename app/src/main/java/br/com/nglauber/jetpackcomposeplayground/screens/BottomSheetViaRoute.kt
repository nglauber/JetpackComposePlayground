package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry

@Composable
fun BottomSheetViaRoute(
    navBackStackEntry: NavBackStackEntry
) {
    val viewModel: MyViewModel = viewModel(navBackStackEntry)
    val list by viewModel.listFlow.collectAsState(emptyList())
    LaunchedEffect(Unit) {
        viewModel.loadList()
    }
    Column(Modifier.verticalScroll(rememberScrollState())) {
        //Text(text = "BottomSheet via route", style = MaterialTheme.typography.h3)
        list.forEach {
            Text(
                text = it,
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}