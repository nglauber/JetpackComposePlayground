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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import br.com.nglauber.jetpackcomposeplayground.MyJavaActivity
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpackcomposeplayground.ROUTE_ACTIVITY_RESULT
import br.com.nglauber.jetpackcomposeplayground.ROUTE_ANIMATING_LIST
import br.com.nglauber.jetpackcomposeplayground.ROUTE_ANIMATION
import br.com.nglauber.jetpackcomposeplayground.ROUTE_AVATAR_STACK
import br.com.nglauber.jetpackcomposeplayground.ROUTE_BACKDROP_SCAFFOLD
import br.com.nglauber.jetpackcomposeplayground.ROUTE_BOOKS
import br.com.nglauber.jetpackcomposeplayground.ROUTE_BOTTOM_NAV
import br.com.nglauber.jetpackcomposeplayground.ROUTE_BOTTOM_SHEET
import br.com.nglauber.jetpackcomposeplayground.ROUTE_BOTTOM_SHEET_NAV
import br.com.nglauber.jetpackcomposeplayground.ROUTE_BOX
import br.com.nglauber.jetpackcomposeplayground.ROUTE_BROADCAST
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CANVAS
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CHANGE_LANGUAGE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_COLLAPSING_EFFECT
import br.com.nglauber.jetpackcomposeplayground.ROUTE_COMPOSABLE_VIEW
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CONSTRAINT
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CONSTRAINT_BARRIER
import br.com.nglauber.jetpackcomposeplayground.ROUTE_COROUTINES
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CUSTOM_NAV_TYPE_SCREEN_1
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CUSTOM_ROUTE_C
import br.com.nglauber.jetpackcomposeplayground.ROUTE_DATE_PICKER
import br.com.nglauber.jetpackcomposeplayground.ROUTE_DERIVED_STATE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_DROPDOWN
import br.com.nglauber.jetpackcomposeplayground.ROUTE_EMPHASIS
import br.com.nglauber.jetpackcomposeplayground.ROUTE_EXIT
import br.com.nglauber.jetpackcomposeplayground.ROUTE_EXPORT_COMPOSABLE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_FLIP_CARD
import br.com.nglauber.jetpackcomposeplayground.ROUTE_FOCUS_REQUEST
import br.com.nglauber.jetpackcomposeplayground.ROUTE_FORM
import br.com.nglauber.jetpackcomposeplayground.ROUTE_FORM2
import br.com.nglauber.jetpackcomposeplayground.ROUTE_GRID
import br.com.nglauber.jetpackcomposeplayground.ROUTE_HEXAGON
import br.com.nglauber.jetpackcomposeplayground.ROUTE_HORIZONTAL_SCROLL
import br.com.nglauber.jetpackcomposeplayground.ROUTE_IMAGE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_IMAGE_ROTATION
import br.com.nglauber.jetpackcomposeplayground.ROUTE_INSTAGRAM_PROGRESS
import br.com.nglauber.jetpackcomposeplayground.ROUTE_KEYBOARD
import br.com.nglauber.jetpackcomposeplayground.ROUTE_LIFECYCLE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_LIST_GRADIENT_BG
import br.com.nglauber.jetpackcomposeplayground.ROUTE_LIST_PARALLAX_IMG
import br.com.nglauber.jetpackcomposeplayground.ROUTE_LIST_STICK_HEADER
import br.com.nglauber.jetpackcomposeplayground.ROUTE_LIST_STICK_HEADER_CUSTOM
import br.com.nglauber.jetpackcomposeplayground.ROUTE_MULTI_SCROLL
import br.com.nglauber.jetpackcomposeplayground.ROUTE_NESTED_SCROLL
import br.com.nglauber.jetpackcomposeplayground.ROUTE_NUMBER_PAD
import br.com.nglauber.jetpackcomposeplayground.ROUTE_PAGING_MARVEL
import br.com.nglauber.jetpackcomposeplayground.ROUTE_REACTIONS_TOUCH
import br.com.nglauber.jetpackcomposeplayground.ROUTE_REVEAL_SWIPE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SCAFFOLD
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SCORE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SCROLL_ANIMATION
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SCROLL_CURVED
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SLIDE_ANIMATION
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SNAPPER
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SOCIAL_NETWORKS
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SPEEDOMETER
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SUBCOMPOSABLE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_SWIPEABLE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_TABLE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_TAKE_PICTURE
import br.com.nglauber.jetpackcomposeplayground.ROUTE_TOUCHABLE_FEEDBACK
import br.com.nglauber.jetpackcomposeplayground.ROUTE_VIEW_PAGER
import br.com.nglauber.jetpackcomposeplayground.ROUTE_VIEW_PAGER_BOTTOM_NAV
import br.com.nglauber.jetpackcomposeplayground.ROUTE_VIEW_PAGER_TABS
import br.com.nglauber.jetpackcomposeplayground.ROUTE_WEB_VIEW
import br.com.nglauber.jetpackcomposeplayground.ROUTE_YOUTUBE

@Composable
fun MainScreen(navController: NavHostController) {
    val listState = rememberLazyListState()
    val names = remember {
        listOf(
            R.string.route_keyboard to ROUTE_KEYBOARD,
            R.string.route_derived_state to ROUTE_DERIVED_STATE,
            R.string.route_number_pad to ROUTE_NUMBER_PAD,
            R.string.route_image to ROUTE_IMAGE,
            R.string.route_image_rotation to ROUTE_IMAGE_ROTATION,
            R.string.route_take_picture to ROUTE_TAKE_PICTURE,
            R.string.route_dropdown to ROUTE_DROPDOWN,
            R.string.route_bottom_nav to ROUTE_BOTTOM_NAV,
            R.string.route_form2 to ROUTE_FORM2,
            R.string.route_form to ROUTE_FORM,
            R.string.route_date_picker to ROUTE_DATE_PICKER,
            R.string.route_focus_request to ROUTE_FOCUS_REQUEST,
            R.string.route_emphasis to ROUTE_EMPHASIS,
            R.string.route_constraint to ROUTE_CONSTRAINT,
            R.string.route_constraint_barrier to ROUTE_CONSTRAINT_BARRIER,
            R.string.route_subcomposable to ROUTE_SUBCOMPOSABLE,
            R.string.route_box to ROUTE_BOX,
            R.string.route_avatar_stack to ROUTE_AVATAR_STACK,
            R.string.route_coroutines to ROUTE_COROUTINES,
            R.string.route_scaffold to ROUTE_SCAFFOLD,
            R.string.route_backdrop_scaffold to ROUTE_BACKDROP_SCAFFOLD,
            R.string.route_social_networks to ROUTE_SOCIAL_NETWORKS,
            R.string.route_score to ROUTE_SCORE,
            R.string.route_books to ROUTE_BOOKS,
            R.string.route_paging_marvel to ROUTE_PAGING_MARVEL,
            R.string.route_animation to ROUTE_ANIMATION,
            R.string.route_animating_list to ROUTE_ANIMATING_LIST,
            R.string.route_slide_animation to ROUTE_SLIDE_ANIMATION,
            R.string.route_instagram_progress to ROUTE_INSTAGRAM_PROGRESS,
            R.string.route_flip_card to ROUTE_FLIP_CARD,
            R.string.route_canvas to ROUTE_CANVAS,
            R.string.route_haxagon to ROUTE_HEXAGON,
            R.string.route_speedometer to ROUTE_SPEEDOMETER,
            R.string.route_bottom_sheet to ROUTE_BOTTOM_SHEET,
            R.string.route_broadcast to ROUTE_BROADCAST,
            R.string.route_activity_result to ROUTE_ACTIVITY_RESULT,
            R.string.route_grid to ROUTE_GRID,
            R.string.route_table to ROUTE_TABLE,
            R.string.route_list_sticky_header to ROUTE_LIST_STICK_HEADER,
            R.string.route_list_sticky_header_custom to ROUTE_LIST_STICK_HEADER_CUSTOM,
            R.string.route_list_gradient_bg to ROUTE_LIST_GRADIENT_BG,
            R.string.route_list_prallax_img to ROUTE_LIST_PARALLAX_IMG,
            R.string.route_reveal_swipe to ROUTE_REVEAL_SWIPE,
            R.string.route_swipeable to ROUTE_SWIPEABLE,
            R.string.route_nested_scroll to ROUTE_NESTED_SCROLL,
            R.string.route_multi_scroll to ROUTE_MULTI_SCROLL,
            R.string.route_horizontal_scroll to ROUTE_HORIZONTAL_SCROLL,
            R.string.route_snapper to ROUTE_SNAPPER,
            R.string.route_collapsing_effect to ROUTE_COLLAPSING_EFFECT,
            R.string.route_scroll_animation to ROUTE_SCROLL_ANIMATION,
            R.string.route_touchable_feedback to ROUTE_TOUCHABLE_FEEDBACK,
            R.string.route_reactions_touch to ROUTE_REACTIONS_TOUCH,
            R.string.route_scroll_curved to ROUTE_SCROLL_CURVED,
            R.string.route_view_pager to ROUTE_VIEW_PAGER,
            R.string.route_view_pager_tabs to ROUTE_VIEW_PAGER_TABS,
            R.string.route_view_pager_bottom_nav to ROUTE_VIEW_PAGER_BOTTOM_NAV,
            R.string.route_composable_view to ROUTE_COMPOSABLE_VIEW,
            R.string.route_lifecycle to ROUTE_LIFECYCLE,
            R.string.route_bottom_nav to ROUTE_BOTTOM_SHEET_NAV,
            R.string.route_custom_route_c to ROUTE_CUSTOM_ROUTE_C,
            R.string.route_custom_nav_type_screen_1 to ROUTE_CUSTOM_NAV_TYPE_SCREEN_1,
            R.string.route_export_composable to ROUTE_EXPORT_COMPOSABLE,
            R.string.route_webview to ROUTE_WEB_VIEW,
            R.string.route_youtube to ROUTE_YOUTUBE,
            R.string.route_change_language to ROUTE_CHANGE_LANGUAGE,
            R.string.route_exit to ROUTE_EXIT
        )
    }

    val activity = (LocalContext.current as? Activity)
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        items(names) { item ->
            val (title, route) = item
            Text(
                stringResource(id = title),
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