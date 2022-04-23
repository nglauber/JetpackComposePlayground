package br.com.nglauber.jetpackcomposeplayground.bottomnav

import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import kotlin.random.Random

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
            TabContent(currentTab, it)
        }
    }
}

@Composable
fun TabContent(tabItem: String, paddingValues: PaddingValues) {
    val tab1NavState =
        rememberSaveable { mutableStateOf(Bundle()) }
    val tab2NavState =
        rememberSaveable { mutableStateOf(Bundle()) }

    when (tabItem) {
        TabItem.ListInfo.route -> {
            TabWrapper(tab1NavState) { navController ->
                TabList(navController, paddingValues)
            }
        }
        TabItem.ProfileInfo.route -> {
            TabWrapper(tab2NavState) { navController ->
                TabProfile(navController, paddingValues)
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
fun TabList(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = "tab1_main") {
        composable("tab1_main") {
            Tab1MainScreen(paddingValues) {
                navController.navigate("tab1_details")
            }
        }
        composable("tab1_details") {
            Tab1DetailsScreen(paddingValues)
        }
    }
}

@Composable
fun TabProfile(navController: NavHostController, paddingValues: PaddingValues) {
    val device = Device(
        Random.nextInt(0, 100).toString(), "test"
    )

    NavHost(navController = navController, startDestination = "tab2_main") {
        composable("tab2_main") {
            Tab2MainScreen(device, paddingValues) {
                val json = Uri.encode(Gson().toJson(device))
                navController.navigate("tab2_details/$json")
            }
        }
        composable(
            "tab2_details/{device}",
            arguments = listOf(
                navArgument("device") {
                    type = AssetParamType()
                }
            )
        ) {
            val prevScreenDevice = it.arguments?.getParcelable<Device>("device")
            Tab2DetailsScreen(prevScreenDevice!!, paddingValues)
        }
    }
}
