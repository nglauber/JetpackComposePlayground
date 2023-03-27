package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun BottomSheetScreen(navBackStackEntry: NavBackStackEntry) {
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomSheetViaRoute(navBackStackEntry = navBackStackEntry)
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