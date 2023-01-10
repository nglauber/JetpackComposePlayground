package br.com.nglauber.jetpackcomposeplayground.bottomnav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

// Main Tabs
private val tabItems = listOf(
    TabItem.Tab1,
    TabItem.Tab2,
)

// Each Tab routes
private const val TAB_1_MAIN = "tab_1_main"
private const val TAB_1_DETAILS = "tab_1_details"
private const val TAB_2_MAIN = "tab_2_main"
private const val TAB_2_DETAILS = "tab_2_details"

@Composable
fun BottomNavScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val selectionMap = remember(currentDestination) {
        tabItems.associateWith { tabItem ->
            (currentDestination?.hierarchy?.any { it.route == tabItem.route } == true)
        }
    }
    Scaffold(bottomBar = {
        BottomNavigation {
            tabItems.forEach { tabItem ->
                val text = stringResource(id = tabItem.title)
                BottomNavigationItem(
                    modifier = Modifier.testTag(text),
                    icon = { Icon(tabItem.icon, text) },
                    label = { Text(text) },
                    selected = selectionMap.getOrDefault(tabItem, false),
                    onClick = {
                        navigate(navController, tabItem.route)
                    }
                )
            }
        }
    }) { paddingValues ->
        NavHost(
            navController = navController, startDestination = TabItem.Tab1.route
        ) {
            navigation(
                route = TabItem.Tab1.route, startDestination = TAB_1_MAIN
            ) {
                composable(TAB_1_MAIN) {
                    Tab1MainScreen(paddingValues, onDetailsSelected = {
                        navController.navigate(TAB_1_DETAILS)
                    })
                }
                composable(TAB_1_DETAILS) {
                    Tab1DetailsScreen(paddingValues, goToTab2details = {
                        navigate(navController, TabItem.Tab2.route)
                        navController.navigate(TAB_2_DETAILS)
                    })
                }
            }
            navigation(
                route = TabItem.Tab2.route, startDestination = TAB_2_MAIN
            ) {
                composable(TAB_2_MAIN) {
                    Tab2MainScreen(paddingValues, onDetailsSelected = {
                        navController.navigate(TAB_2_DETAILS)
                    }, onBackPressed = {
                        navigate(navController, TabItem.Tab1.route)
                    })
                }
                composable(TAB_2_DETAILS) {
                    Tab2DetailsScreen(paddingValues)
                }
            }
        }
    }
}

private fun navigate(
    navController: NavHostController, route: String
) {
    navController.navigate(route) {
        val navigationRoutes = tabItems.map { it.route }
        val firstBottomBarDestination = navController.currentBackStack.value.firstOrNull {
            navigationRoutes.contains(it.destination.route)
        }?.destination
        // remove all navigation items from the stack
        // so only the currently selected screen remains in the stack
        if (firstBottomBarDestination != null) {
            popUpTo(firstBottomBarDestination.id) {
                inclusive = true
                saveState = true
            }
        }
        // Avoid multiple copies of the same destination when reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}
