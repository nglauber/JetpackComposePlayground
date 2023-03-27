package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import br.com.nglauber.jetpackcomposeplayground.R
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun BottomNavSwipeScreen() {
    val scope = rememberCoroutineScope()
    val images = listOf(
        R.drawable.recife,
        R.drawable.dog,
        R.drawable.male,
        R.drawable.female,
    )
    val pageState = rememberPagerState(initialPage = 0)

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = {
                it != ModalBottomSheetValue.HalfExpanded
            }
        )
    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            DatePickerScreen()
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("App bar")
                    },
                    actions = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (modalBottomSheetState.isVisible) {
                                    modalBottomSheetState.hide()
                                } else {
                                    modalBottomSheetState.show()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Home, null)
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    content = {
                        for (page in images.indices) {
                            BottomNavigationItem(
                                icon = { Icon(Icons.Filled.Home, "Page $page") },
                                selected = page == pageState.currentPage,
                                onClick = {
                                    scope.launch {
                                        pageState.animateScrollToPage(page)
                                    }
                                },
                                selectedContentColor = Color.Magenta,
                                unselectedContentColor = Color.LightGray,
                                label = { Text(text = "Page $page") }
                            )
                        }
                    }
                )
            },
        ) {
            HorizontalPager(
                state = pageState,
                pageCount = images.size,
                modifier = Modifier.padding(it),
            ) { page ->
                Image(
                    painterResource(id = images[page]),
                    null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}