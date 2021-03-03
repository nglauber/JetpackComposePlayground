package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun BottomSheetScreen() {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            CanvasSample()
        }
    ) {
        Button(onClick = {
            scope.launch {
                bottomSheetState.show()
            }
        }) {
            Text("Open BottomSheet")
        }
    }
}