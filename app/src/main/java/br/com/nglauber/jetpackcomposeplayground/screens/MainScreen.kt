package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import br.com.nglauber.jetpackcomposeplayground.*

@Composable
fun MainScreen(navController: NavHostController) {
    val listState = rememberLazyListState()
    val names = remember {
        listOf(
            "Keyboard with ConstraintLayout" to ROUTE_KEYBOARD,
            "Image" to ROUTE_IMAGE,
            "Take Picture" to ROUTE_TAKE_PICTURE,
            "DropDown" to ROUTE_DROPDOWN,
            "Bottom Nav" to ROUTE_BOTTOM_NAV,
            "Form" to ROUTE_FORM2,
            "Form + Android View" to ROUTE_FORM,
            "Focus Request" to ROUTE_FOCUS_REQUEST,
            "Emphasis" to ROUTE_EMPHASIS,
            "ConstraintLayout" to ROUTE_CONSTRAINT,
            "Box/Stack/Frame Layout" to ROUTE_BOX,
            "Coroutines" to ROUTE_COROUTINES,
            "Scaffold" to ROUTE_SCAFFOLD,
            "BackDrop Scaffold" to ROUTE_BACKDROP_SCAFFOLD,
            "Social Networks" to ROUTE_SOCIAL_NETWORKS,
            "Score" to ROUTE_SCORE,
            "Books Http" to ROUTE_BOOKS,
            "Animation" to ROUTE_ANIMATION,
            "Canvas" to ROUTE_CANVAS,
            "BottomSheet" to ROUTE_BOTTOM_SHEET,
            "Broadcast" to ROUTE_BROADCAST,
            "Activity Result" to ROUTE_ACTIVITY_RESULT,
            "Grid" to ROUTE_GRID,
            "List with Stick Header" to ROUTE_LIST_STICK_HEADER,
            "Nested Scroll" to ROUTE_NESTED_SCROLL,
            "ViewPager" to ROUTE_VIEW_PAGER,
            "ViewPager + Tabs" to ROUTE_VIEW_PAGER_TABS,
        )
    }

    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        items(names) { item ->
            val (title, route) = item
            Text(
                title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable(onClick = {
                        navController.navigate(route)
                    })
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}