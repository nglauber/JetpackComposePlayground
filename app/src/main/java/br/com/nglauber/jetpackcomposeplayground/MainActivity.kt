package br.com.nglauber.jetpackcomposeplayground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.nglauber.jetpackcomposeplayground.bottomnav.BottomNavScreen
import br.com.nglauber.jetpackcomposeplayground.crud.SocialNetworkScreen
import br.com.nglauber.jetpackcomposeplayground.rest.BooksScreen
import br.com.nglauber.jetpackcomposeplayground.screens.*
import br.com.nglauber.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : AppCompatActivity() {

    @ExperimentalComposeUiApi
    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            JetpackComposePlaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = ROUTE_MAIN) {
                        composable(ROUTE_MAIN) { MainScreen(navController) }
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
                        composable(ROUTE_BOX) { BoxScreen() }
                        composable(ROUTE_COROUTINES) { CoroutinesScreen() }
                        composable(ROUTE_SCAFFOLD) { ScaffoldScreen() }
                        composable(ROUTE_BACKDROP_SCAFFOLD) { BackdropScaffoldScreen() }
                        composable(ROUTE_SOCIAL_NETWORKS) { SocialNetworkScreen() }
                        composable(ROUTE_SCORE) { ScoreScreen() }
                        composable(ROUTE_BOOKS) { BooksScreen() }
                        composable(ROUTE_ANIMATION) { AnimationScreen() }
                        composable(ROUTE_SLIDE_ANIMATION) { SlideInAnimationScreen() }
                        composable(ROUTE_CANVAS) { CanvasScreen() }
                        composable(ROUTE_BOTTOM_SHEET) { BottomSheetScreen() }
                        composable(ROUTE_BROADCAST) { BroadcastScreen() }
                        composable(ROUTE_ACTIVITY_RESULT) { GetActivityResultScreen() }
                        composable(ROUTE_GRID) { GridScreen() }
                        composable(ROUTE_LIST_STICK_HEADER) { ListWithStickHeaderScreen() }
                        composable(ROUTE_NESTED_SCROLL) { NestedScrollScreen() }
                        composable(ROUTE_HORIZONTAL_SCROLL) { HorizontalScrollScreen() }
                        composable(ROUTE_VIEW_PAGER) { ViewPagerScreen() }
                        composable(ROUTE_VIEW_PAGER_TABS) { ViewPagerTabsScreen() }
                        composable(ROUTE_VIEW_PAGER_BOTTOM_NAV) { BottomNavSwipeScreen() }
                        composable(ROUTE_COLLAPSING_EFFECT) { CollapsingEffectScreen() }
                        composable(ROUTE_REVEAL_SWIPE) { RevealSwipeScreen() }
                    }
                }
            }
        }
    }
}