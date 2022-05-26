package com.danik.tutuapp.presentation.screens.details

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.danik.tutuapp.util.Constants.BASE_URL
import com.danik.tutuapp.util.PaletteGenerator.convertStringToBitmap
import com.danik.tutuapp.util.PaletteGenerator.extractColorFromBitmap


@Composable
fun DetailsScreen(
    navHostController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val selectedTrain by detailsViewModel.selectedTrain.collectAsState()
    val colorsPalette by detailsViewModel.colorPalette
    val context = LocalContext.current

    if (colorsPalette.isNotEmpty()) {
        DetailsContent(
            navHostController = navHostController,
            selectedTrain = selectedTrain,
            colors = colorsPalette
        )
    } else {
        detailsViewModel.startUiEvent()
        detailsViewModel.setPalette(colors = colorsPalette)
    }
    LaunchedEffect(key1 = true) {

        detailsViewModel.uiEvent.collect { state ->
            when (state) {
                UiState.GenerateColorPalette -> {

                    val bitMap = convertStringToBitmap(
                        imageUrl = "$BASE_URL${selectedTrain?.image}",
                        context = context
                    )
                    Log.d("DetailsScreen", "$bitMap")
                    if (bitMap != null) {
                        detailsViewModel.setPalette(
                            extractColorFromBitmap(bitmap = bitMap)
                        )
                    }
                }
            }

        }
    }
}