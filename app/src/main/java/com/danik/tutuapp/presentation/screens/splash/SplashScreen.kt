package com.danik.tutuapp.presentation.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.danik.tutuapp.R
import com.danik.tutuapp.navigation.Screen
import com.danik.tutuapp.ui.theme.Purple500
import com.danik.tutuapp.ui.theme.Purple700
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {


    var animate by remember {
        mutableStateOf(true)
    }

    val modifier = if (isSystemInDarkTheme()) {
        Modifier.background(Color.Black)
    } else {
        Modifier.background(
            Brush.verticalGradient(listOf(Purple700, Purple500))
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        Alignment.Center
    ) {
        AnimatedVisibility(
            exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) {
                600
            } + fadeOut(),
            visible = animate,
            content = {
                Splash()
            }

        )
    }
    LaunchedEffect(key1 = true) {
        delay(1000)
        animate = false
        delay(500)
        navHostController.popBackStack()
        navHostController.navigate(Screen.Main.route)
    }


}

@Composable
fun Splash() {
    Image(
        painter = painterResource(R.drawable.ic_train),
        contentDescription = stringResource(R.string.splash_screen_logo),
    )
}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash()
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenDarkPreview() {
    Splash()
}