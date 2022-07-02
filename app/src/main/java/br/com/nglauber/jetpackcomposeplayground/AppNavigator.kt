package br.com.nglauber.jetpackcomposeplayground

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import br.com.nglauber.jetpackcomposeplayground.bottomnav.BottomNavScreen
import br.com.nglauber.jetpackcomposeplayground.crud.SocialNetworkScreen
import br.com.nglauber.jetpackcomposeplayground.paging.MarvelCharactersScreen
import br.com.nglauber.jetpackcomposeplayground.paging.MarvelCharactersViewModel
import br.com.nglauber.jetpackcomposeplayground.rest.BooksScreen
import br.com.nglauber.jetpackcomposeplayground.rest2.DogsViewModel
import br.com.nglauber.jetpackcomposeplayground.screens.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@ExperimentalAnimationGraphicsApi
@ExperimentalSnapperApi
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
    composable(ROUTE_IMAGE_ROTATION) { OneFingerImageRotationScreen() }
    composable(ROUTE_TAKE_PICTURE) { TakePictureScreen() }
    composable(ROUTE_DROPDOWN) { DropDownScreen() }
    composable(ROUTE_FORM) { FormScreen() }
    composable(ROUTE_FORM2) { Form2Screen() }
    composable(ROUTE_DATE_PICKER) { DatePickerScreen() }
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
    composable(ROUTE_PAGING_MARVEL) {
        val viewModel = viewModel<MarvelCharactersViewModel>()
        MarvelCharactersScreen(viewModel)
    }
    composable(ROUTE_ANIMATION) { AnimationScreen() }
    composable(ROUTE_ANIMATING_LIST) { AnimatingListScreen() }
    composable(ROUTE_INSTAGRAM_PROGRESS) { MyInstagramScreen() }
    composable(ROUTE_SLIDE_ANIMATION) { SlideInAnimationScreen() }
    composable(ROUTE_FLIP_CARD) { FlipCardScreen() }
    composable(ROUTE_CANVAS) { CanvasScreen() }
    composable(ROUTE_SPEEDOMETER) { SpeedometerScreen() }
    composable(ROUTE_BOTTOM_SHEET) { BottomSheetScreen(it) }
    composable(ROUTE_BROADCAST) { BroadcastScreen() }
    composable(ROUTE_ACTIVITY_RESULT) { GetActivityResultScreen() }
    composable(ROUTE_GRID) { GridScreen() }
    composable(ROUTE_TABLE) { TableScreen() }
    composable(ROUTE_LIST_STICK_HEADER) { ListWithStickHeaderScreen() }
    composable(ROUTE_LIST_STICK_HEADER_CUSTOM) { ListWithCustomStickHeaderScreen() }
    composable(ROUTE_LIST_GRADIENT_BG) { ListWithGradientBgScreen() }
    composable(ROUTE_LIST_PARALLAX_IMG) { ListWithParallaxImageScreen() }
    composable(ROUTE_NESTED_SCROLL) {
        val viewModel = viewModel<DogsViewModel>()
        NestedScrollScreen(viewModel)
    }
    composable(ROUTE_MULTI_SCROLL) { MultiScrollScreen() }
    composable(ROUTE_HORIZONTAL_SCROLL) { HorizontalScrollScreen() }
    composable(ROUTE_SNAPPER) { RowSnapperScreen() }
    composable(ROUTE_VIEW_PAGER) { ViewPagerScreen() }
    composable(ROUTE_VIEW_PAGER_TABS) { ViewPagerTabsScreen() }
    composable(ROUTE_VIEW_PAGER_BOTTOM_NAV) { BottomNavSwipeScreen() }
    composable(ROUTE_COLLAPSING_EFFECT) { CollapsingEffectScreen() }
    composable(ROUTE_SCROLL_ANIMATION) { ScrollAnimationScreen() }
    composable(ROUTE_TOUCHABLE_FEEDBACK) { TouchableFeedback() }
    composable(ROUTE_REACTIONS_TOUCH) { ReactionsTouchScreen() }
    composable(ROUTE_SCROLL_CURVED) { CurvedScrollScreen() }
    composable(ROUTE_REVEAL_SWIPE) { RevealSwipeScreen() }
    composable(ROUTE_SWIPEABLE) { SwipeableSampleScreen5() }
    composable(ROUTE_LIFECYCLE) { LifecycleSampleScreen() }
    composable(ROUTE_EXPORT_COMPOSABLE) { ExportComposableScreen() }
    bottomSheet(ROUTE_BOTTOM_SHEET_NAV) { BottomSheetViaRoute(it) }
    composable(ROUTE_CUSTOM_ROUTE_A) { CustomBackStackScreenA(navController) }
    composable(ROUTE_CUSTOM_ROUTE_B) { CustomBackStackScreenB(navController) }
    composable(ROUTE_CUSTOM_ROUTE_C) { CustomBackStackScreenC(navController) }
    composable(ROUTE_CUSTOM_NAV_TYPE_SCREEN_1) {
        CustomNavTypeScreen1(onDeviceSelected = {
            navController.navigate("$ROUTE_CUSTOM_NAV_TYPE_SCREEN_2/$it")
        })
    }
    composable(
        "$ROUTE_CUSTOM_NAV_TYPE_SCREEN_2/{device}",
        arguments = listOf(
            navArgument("device") {
                type = AssetParamType()
            }
        )
    ) {
        val prevScreenDevice = it.arguments?.getParcelable<Device>("device")
        CustomNavTypeScreen2(prevScreenDevice)
    }
}