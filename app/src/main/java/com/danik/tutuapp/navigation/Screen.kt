package com.danik.tutuapp.navigation

sealed class Screen(val route: String){

    object Splash: Screen("splash_screen")

    object Main: Screen("main_screen")

    object Detail: Screen("details_screen/{trainId}"){
        fun passArgument(trainId: Int): String{
            return "details_screen/$trainId"
        }
    }
}
