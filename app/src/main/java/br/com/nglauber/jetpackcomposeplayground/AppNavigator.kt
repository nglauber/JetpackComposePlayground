package br.com.nglauber.jetpackcomposeplayground

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.nglauber.jetpackcomposeplayground.bottomnav.BottomNavScreen
import br.com.nglauber.jetpackcomposeplayground.crud.SocialNetworkScreen
import br.com.nglauber.jetpackcomposeplayground.paging.MarvelCharactersScreen
import br.com.nglauber.jetpackcomposeplayground.paging.MarvelCharactersViewModel
import br.com.nglauber.jetpackcomposeplayground.rest.BooksScreen
import br.com.nglauber.jetpackcomposeplayground.rest2.DogsViewModel
import br.com.nglauber.jetpackcomposeplayground.screens.AnimatingListScreen
import br.com.nglauber.jetpackcomposeplayground.screens.AnimationScreen
import br.com.nglauber.jetpackcomposeplayground.screens.AvatarStackScreen
import br.com.nglauber.jetpackcomposeplayground.screens.BackdropScaffoldScreen
import br.com.nglauber.jetpackcomposeplayground.screens.BottomNavSwipeScreen
import br.com.nglauber.jetpackcomposeplayground.screens.BottomSheetScreen
import br.com.nglauber.jetpackcomposeplayground.screens.BottomSheetViaRoute
import br.com.nglauber.jetpackcomposeplayground.screens.BoxScreen
import br.com.nglauber.jetpackcomposeplayground.screens.BroadcastScreen
import br.com.nglauber.jetpackcomposeplayground.screens.CanvasScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ChangeLanguageScreen
import br.com.nglauber.jetpackcomposeplayground.screens.CollapsingEffectScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ConstraintLayoutBarrierScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ConstraintLayoutScreen
import br.com.nglauber.jetpackcomposeplayground.screens.CoroutinesScreen
import br.com.nglauber.jetpackcomposeplayground.screens.CurvedScrollScreen
import br.com.nglauber.jetpackcomposeplayground.screens.CustomBackStackScreenA
import br.com.nglauber.jetpackcomposeplayground.screens.CustomBackStackScreenB
import br.com.nglauber.jetpackcomposeplayground.screens.CustomBackStackScreenC
import br.com.nglauber.jetpackcomposeplayground.screens.CustomNavTypeScreen1
import br.com.nglauber.jetpackcomposeplayground.screens.CustomNavTypeScreen2
import br.com.nglauber.jetpackcomposeplayground.screens.DatePickerScreen
import br.com.nglauber.jetpackcomposeplayground.screens.DerivedStateScreen
import br.com.nglauber.jetpackcomposeplayground.screens.Device
import br.com.nglauber.jetpackcomposeplayground.screens.DropDownScreen
import br.com.nglauber.jetpackcomposeplayground.screens.EmphasisScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ExportComposableScreen
import br.com.nglauber.jetpackcomposeplayground.screens.FlipCardScreen
import br.com.nglauber.jetpackcomposeplayground.screens.FocusRequestScreen
import br.com.nglauber.jetpackcomposeplayground.screens.Form2Screen
import br.com.nglauber.jetpackcomposeplayground.screens.FormScreen
import br.com.nglauber.jetpackcomposeplayground.screens.GetActivityResultScreen
import br.com.nglauber.jetpackcomposeplayground.screens.GridScreen
import br.com.nglauber.jetpackcomposeplayground.screens.HexagonShapeScreen
import br.com.nglauber.jetpackcomposeplayground.screens.HorizontalScrollScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ImageScreen
import br.com.nglauber.jetpackcomposeplayground.screens.KeyboardScreen
import br.com.nglauber.jetpackcomposeplayground.screens.LifecycleSampleScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ListWithCustomStickHeaderScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ListWithGradientBgScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ListWithParallaxImageScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ListWithStickHeaderScreen
import br.com.nglauber.jetpackcomposeplayground.screens.MainScreen
import br.com.nglauber.jetpackcomposeplayground.screens.MultiScrollScreen
import br.com.nglauber.jetpackcomposeplayground.screens.MyInstagramScreen
import br.com.nglauber.jetpackcomposeplayground.screens.NestedScrollScreen
import br.com.nglauber.jetpackcomposeplayground.screens.NumberPadScreen
import br.com.nglauber.jetpackcomposeplayground.screens.OneFingerImageRotationScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ReactionsTouchScreen
import br.com.nglauber.jetpackcomposeplayground.screens.RevealSwipeScreen
import br.com.nglauber.jetpackcomposeplayground.screens.RowSnapperScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ScaffoldScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ScoreScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ScrollAnimationScreen
import br.com.nglauber.jetpackcomposeplayground.screens.SlideInAnimationScreen
import br.com.nglauber.jetpackcomposeplayground.screens.SpeedometerScreen
import br.com.nglauber.jetpackcomposeplayground.screens.SubcomposableSampleScreen
import br.com.nglauber.jetpackcomposeplayground.screens.SwipeableSampleScreen5
import br.com.nglauber.jetpackcomposeplayground.screens.TableScreen
import br.com.nglauber.jetpackcomposeplayground.screens.TakePictureScreen
import br.com.nglauber.jetpackcomposeplayground.screens.TouchableFeedback
import br.com.nglauber.jetpackcomposeplayground.screens.ViewPagerScreen
import br.com.nglauber.jetpackcomposeplayground.screens.ViewPagerTabsScreen
import br.com.nglauber.jetpackcomposeplayground.screens.WebViewScreen
import br.com.nglauber.jetpackcomposeplayground.screens.YouTubeScreen
import br.com.nglauber.jetpackcomposeplayground.util.navigate
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@ExperimentalCoilApi
@ExperimentalAnimationGraphicsApi
@ExperimentalSnapperApi
@ExperimentalMaterialNavigationApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.appNavigator(
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
    composable(ROUTE_HEXAGON) { HexagonShapeScreen() }
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
            navController.navigate(
                ROUTE_CUSTOM_NAV_TYPE_SCREEN_2, bundleOf("device" to it)
            )
        })
    }
    composable(ROUTE_CUSTOM_NAV_TYPE_SCREEN_2) {
        val prevScreenDevice = it.arguments?.getParcelable<Device>("device")
        CustomNavTypeScreen2(prevScreenDevice)
    }
    composable(ROUTE_WEB_VIEW) { WebViewScreen() }
    composable(ROUTE_YOUTUBE) { YouTubeScreen() }
    composable(ROUTE_CHANGE_LANGUAGE) { ChangeLanguageScreen() }
}