package com.danik.tutuapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.danik.tutuapp.presentation.screens.details.DetailsScreen
import com.danik.tutuapp.presentation.screens.main.MainScreen
import com.danik.tutuapp.presentation.screens.splash.SplashScreen
import com.danik.tutuapp.util.Constants.TRAIN_ID_ARGUMENT_KEY

@Composable
fun SetupNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navHostController = navController)
        }
        composable(Screen.Main.route) {
            MainScreen(navHostController = navController)
        }
        composable(
            Screen.Detail.route,
            arguments = listOf(
                navArgument(
                   TRAIN_ID_ARGUMENT_KEY
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            DetailsScreen(navHostController = navController)
        }
    }
}