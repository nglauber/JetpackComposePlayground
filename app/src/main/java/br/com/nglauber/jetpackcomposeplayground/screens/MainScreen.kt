package br.com.nglauber.jetpackcomposeplayground.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import br.com.nglauber.jetpackcomposeplayground.*

@Composable
fun MainScreen(navController: NavHostController) {
    val listState = rememberLazyListState()
    val names = remember {
        listOf(
            "Keyboard with ConstraintLayout" to ROUTE_KEYBOARD,
            "Derived State and Side Effect" to ROUTE_DERIVED_STATE,
            "Number pad" to ROUTE_NUMBER_PAD,
            "Image" to ROUTE_IMAGE,
            "Take Picture" to ROUTE_TAKE_PICTURE,
            "DropDown" to ROUTE_DROPDOWN,
            "Bottom Nav" to ROUTE_BOTTOM_NAV,
            "Form" to ROUTE_FORM2,
            "Form + Android View" to ROUTE_FORM,
            "Date Picker" to ROUTE_DATE_PICKER,
            "Focus Request" to ROUTE_FOCUS_REQUEST,
            "Emphasis" to ROUTE_EMPHASIS,
            "ConstraintLayout" to ROUTE_CONSTRAINT,
            "ConstraintLayout | Barrier" to ROUTE_CONSTRAINT_BARRIER,
            "Subcomposable" to ROUTE_SUBCOMPOSABLE,
            "Box/Stack/Frame Layout" to ROUTE_BOX,
            "Avatar Stack" to ROUTE_AVATAR_STACK,
            "Coroutines" to ROUTE_COROUTINES,
            "Scaffold" to ROUTE_SCAFFOLD,
            "BackDrop Scaffold" to ROUTE_BACKDROP_SCAFFOLD,
            "Social Networks" to ROUTE_SOCIAL_NETWORKS,
            "Score" to ROUTE_SCORE,
            "Books Http" to ROUTE_BOOKS,
            "Animation" to ROUTE_ANIMATION,
            "AnimatingList" to ROUTE_ANIMATING_LIST,
            "Slide Animation" to ROUTE_SLIDE_ANIMATION,
            "Instagram Progress" to ROUTE_INSTAGRAM_PROGRESS,
            "Flip card" to ROUTE_FLIP_CARD,
            "Canvas" to ROUTE_CANVAS,
            "Speedometer" to ROUTE_SPEEDOMETER,
            "BottomSheet" to ROUTE_BOTTOM_SHEET,
            "Broadcast" to ROUTE_BROADCAST,
            "Activity Result" to ROUTE_ACTIVITY_RESULT,
            "Grid" to ROUTE_GRID,
            "Table" to ROUTE_TABLE,
            "List with Stick Header" to ROUTE_LIST_STICK_HEADER,
            "Reveal Swipe" to ROUTE_REVEAL_SWIPE,
            "Nested Scroll" to ROUTE_NESTED_SCROLL,
            "Horizontal Scroll" to ROUTE_HORIZONTAL_SCROLL,
            "Lazy Row Snapper" to ROUTE_SNAPPER,
            "Collapsing Effect" to ROUTE_COLLAPSING_EFFECT,
            "Scroll Animation" to ROUTE_SCROLL_ANIMATION,
            "Touchable feedback" to ROUTE_TOUCHABLE_FEEDBACK,
            "Curved Scroll" to ROUTE_SCROLL_CURVED,
            "ViewPager (Lazy Load Content)" to ROUTE_VIEW_PAGER,
            "ViewPager + Tabs" to ROUTE_VIEW_PAGER_TABS,
            "ViewPager + Bottom Nav" to ROUTE_VIEW_PAGER_BOTTOM_NAV,
            "Composable View" to ROUTE_COMPOSABLE_VIEW,
            "Lifecycle Sample" to ROUTE_LIFECYCLE,
            "Route BottomSheet" to ROUTE_BOTTOM_SHEET_NAV,
            "Custom backstack" to ROUTE_CUSTOM_ROUTE_C,
            "Export Composable" to ROUTE_EXPORT_COMPOSABLE,
            "Exit" to ROUTE_EXIT
        )
    }

    val activity = (LocalContext.current as? Activity)
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        items(names) { item ->
            val (title, route) = item
            Text(
                title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .clickable(onClick = {
                        when (route) {
                            ROUTE_EXIT ->
                                activity?.finish()
                            ROUTE_COMPOSABLE_VIEW ->
                                activity?.startActivity(
                                    Intent(activity, MyJavaActivity::class.java)
                                )
                            else -> {
                                NavOptionsBuilder()
                                    .anim { }
                                navController.navigate(route)
                            }
                        }
                    })
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Divider()
        }
    }
}