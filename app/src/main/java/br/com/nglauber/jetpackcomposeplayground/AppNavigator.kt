package br.com.nglauber.jetpackcomposeplayground

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.nglauber.jetpackcomposeplayground.bottomnav.BottomNavScreen
import br.com.nglauber.jetpackcomposeplayground.crud.SocialNetworkScreen
import br.com.nglauber.jetpackcomposeplayground.rest.BooksScreen
import br.com.nglauber.jetpackcomposeplayground.screens.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalMaterialNavigationApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.AppNavigator(
    navController: NavHostController
) {
    composable(ROUTE_MAIN) { MainScreen(navController) }
    composable(ROUTE_DERIVED_STATE) { DerivedStateScreen() }
    composable(ROUTE_BOTTOM_NAV) { BottomNavScreen() }
    composable(ROUTE_KEYBOARD) { KeyboardScreen() }
    composable(ROUTE_NUMBER_PAD) { NumberPadScreen() }
    composable(ROUTE_IMAGE) { ImageScreen() }
    composable(ROUTE_TAKE_PICTURE) { TakePictureScreen() }
    composable(ROUTE_DROPDOWN) { DropDownScreen() }
    composable(ROUTE_FORM) { FormScreen() }
    composable(ROUTE_FORM2) { Form2Screen() }
    composable(ROUTE_FOCUS_REQUEST) { FocusRequestScreen() }
    composable(ROUTE_EMPHASIS) { EmphasisScreen() }
    composable(ROUTE_CONSTRAINT) { ConstraintLayoutScreen() }
    composable(ROUTE_CONSTRAINT_BARRIER) { ConstraintLayoutBarrierScreen() }
    composable(ROUTE_SUBCOMPOSABLE) { SubcomposableSampleScreen() }
    composable(ROUTE_BOX) { BoxScreen() }
    composable(ROUTE_AVATAR_STACK) { AvatarStackScreen() }
    composable(ROUTE_COROUTINES) { CoroutinesScreen() }
    composable(ROUTE_SCAFFOLD) { ScaffoldScreen() }
    composable(ROUTE_BACKDROP_SCAFFOLD) { BackdropScaffoldScreen() }
    composable(ROUTE_SOCIAL_NETWORKS) { SocialNetworkScreen() }
    composable(ROUTE_SCORE) { ScoreScreen() }
    composable(ROUTE_BOOKS) { BooksScreen() }
    composable(ROUTE_ANIMATION) { AnimationScreen() }
    composable(ROUTE_INSTAGRAM_PROGRESS) { MyInstagramScreen() }
    composable(ROUTE_SLIDE_ANIMATION) { SlideInAnimationScreen() }
    composable(ROUTE_FLIP_CARD) { FlipCardScreen() }
    composable(ROUTE_CANVAS) { CanvasScreen() }
    composable(ROUTE_BOTTOM_SHEET) { BottomSheetScreen(it) }
    composable(ROUTE_BROADCAST) { BroadcastScreen() }
    composable(ROUTE_ACTIVITY_RESULT) { GetActivityResultScreen() }
    composable(ROUTE_GRID) { GridScreen() }
    composable(ROUTE_TABLE) { TableScreen() }
    composable(ROUTE_LIST_STICK_HEADER) { ListWithStickHeaderScreen() }
    composable(ROUTE_NESTED_SCROLL) { NestedScrollScreen() }
    composable(ROUTE_HORIZONTAL_SCROLL) { HorizontalScrollScreen() }
    composable(ROUTE_VIEW_PAGER) { ViewPagerScreen() }
    composable(ROUTE_VIEW_PAGER_TABS) { ViewPagerTabsScreen() }
    composable(ROUTE_VIEW_PAGER_BOTTOM_NAV) { BottomNavSwipeScreen() }
    composable(ROUTE_COLLAPSING_EFFECT) { CollapsingEffectScreen() }
    composable(ROUTE_SCROLL_ANIMATION) { ScrollAnimationScreen() }
    composable(ROUTE_SCROLL_CURVED) { CurvedScrollScreen() }
    composable(ROUTE_REVEAL_SWIPE) { RevealSwipeScreen() }
    composable(ROUTE_LIFECYCLE) { LifecycleSampleScreen() }
    bottomSheet(ROUTE_BOTTOM_SHEET_NAV) { BottomSheetViaRoute(it) }
}