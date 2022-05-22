package com.danik.tutuapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danik.tutuapp.presentation.screens.main.MainScreen
import com.danik.tutuapp.presentation.screens.splash.SplashScreen

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
    }
}