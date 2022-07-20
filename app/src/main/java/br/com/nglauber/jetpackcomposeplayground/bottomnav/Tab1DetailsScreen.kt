package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.nglauber.jetpackcomposeplayground.R

@Composable
fun Tab1DetailsScreen(paddingValues: PaddingValues, goToTab2details: () -> Unit) {
    Column(Modifier.padding(paddingValues)) {
        Text(text = stringResource(id = R.string.tab_1_details))
        Button(onClick = goToTab2details) {
            Text(stringResource(id = R.string.btn_tab_2_details))
        }
    }
}