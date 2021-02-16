package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable

@ExperimentalMaterialApi
@Composable
fun BottomSheetScreen() {
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            CanvasSample()
        }
    ) {
        Button(onClick = {
            bottomSheetState.show()
        }) {
            Text("Open BottomSheet")
        }
    }
}