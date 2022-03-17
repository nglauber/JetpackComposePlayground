package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CUSTOM_ROUTE_A
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CUSTOM_ROUTE_B
import br.com.nglauber.jetpackcomposeplayground.ROUTE_CUSTOM_ROUTE_C

@Composable
fun CustomBackStackScreenA(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                navController.navigate(ROUTE_CUSTOM_ROUTE_B)
            }
        ) {
            Text(text = "Screen A\n\nGo to B")
        }
        Text(text = "This is the first screen of the flow.\nPressing back should show Main screen.")
    }
}

@Composable
fun CustomBackStackScreenB(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                navController.navigate(ROUTE_CUSTOM_ROUTE_C)
            }
        ) {
            Text(text = "Screen B\n\nGo to C")
        }
        Text(text = "This is the second screen of the flow.\nPressing back should show Screen A")
    }
    BackHandler {
        navigatingBack(navController, ROUTE_CUSTOM_ROUTE_A)
    }
}

@Composable
fun CustomBackStackScreenC(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Screen C")
        Text(text = "This is the third screen of the flow.\nPressing back should show Screen B")
    }
    BackHandler {
        navigatingBack(navController, ROUTE_CUSTOM_ROUTE_B)
    }
}

fun navigatingBack(
    navController: NavHostController,
    destinationRoute: String
) {
    val hasBackstackTheDestinationRoute = navController.backQueue.find {
        it.destination.route == destinationRoute
    } != null
    // if the destination is already in the backstack, simply go back
    if (hasBackstackTheDestinationRoute) {
        navController.popBackStack()
    } else {
        // otherwise, navigate to a new destination popping the current destination
        navController.navigate(destinationRoute) {
            navController.currentBackStackEntry?.destination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                }
            }
        }
    }
}