package br.com.nglauber.jetpackcomposeplayground.bottomnav

import android.os.Bundle
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

typealias BackToFirstTab = () -> Unit

val localBackToFirstTab = compositionLocalOf<BackToFirstTab> {
    error("Invalid backstack")
}

@Composable
fun BottomNavScreen() {
    var currentTab by rememberSaveable { mutableStateOf(TabItem.ListInfo.route) }
    val items = listOf(
        TabItem.ListInfo,
        TabItem.ProfileInfo,
    )
    Scaffold(
        bottomBar = {
            BottomNavigation {
                items.forEach { tabItem ->
                    BottomNavigationItem(
                        icon = { Icon(tabItem.icon, tabItem.title) },
                        label = { Text(tabItem.title) },
                        selected = tabItem.route == currentTab,
                        onClick = {
                            currentTab = tabItem.route
                        }
                    )
                }
            }
        }
    ) {
        CompositionLocalProvider(localBackToFirstTab provides {
            currentTab = TabItem.ListInfo.route
        }) {
            TabContent(currentTab)
        }
    }
}

@Composable
fun TabContent(tabItem: String) {
    val tab1NavState =
        rememberSaveable { mutableStateOf(Bundle()) }
    val tab2NavState =
        rememberSaveable { mutableStateOf(Bundle()) }

    when (tabItem) {
        TabItem.ListInfo.route -> {
            TabWrapper(tab1NavState) { navController ->
                TabList(navController)
            }
        }
        TabItem.ProfileInfo.route -> {
            TabWrapper(tab2NavState) { navController ->
                TabProfile(navController)
            }
        }
    }
}

@Composable
fun TabWrapper(
    navState: MutableState<Bundle>,
    content: @Composable (NavHostController) -> Unit
) {
    val navController = rememberNavController()
    val callback by rememberUpdatedState(
        NavController.OnDestinationChangedListener { _, _, _ ->
            navState.value = navController.saveState() ?: Bundle()
        }
    )
    DisposableEffect(navController) {
        navController.addOnDestinationChangedListener(callback)
        navController.restoreState(navState.value)
        onDispose {
            navController.removeOnDestinationChangedListener(callback)
            navController.enableOnBackPressed(false)
        }
    }
    content(navController)
}

@Composable
fun TabList(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "tab1_main") {
        composable("tab1_main") { Tab1MainScreen(navController) }
        composable("tab1_details") { Tab1DetailsScreen() }
    }
}

@Composable
fun TabProfile(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "tab2_main") {
        composable("tab2_main") { Tab2MainScreen(navController) }
        composable("tab2_details") { Tab2DetailsScreen(navController) }
    }
}
