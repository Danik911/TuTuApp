package com.danik.tutuapp.presentation.screens.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.danik.tutuapp.ui.theme.topAppBarBackgroundColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(
    navHostController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val allTrains = viewModel.allTrains.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    val topAppBarBackgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    SideEffect {
        systemUiController.setStatusBarColor(color = topAppBarBackgroundColor)
    }
    Scaffold(
        content = {
            ListContent(trains = allTrains, navController = navHostController)
        }
    )
}