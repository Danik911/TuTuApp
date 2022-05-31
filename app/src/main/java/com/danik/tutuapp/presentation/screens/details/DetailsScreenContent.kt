package com.danik.tutuapp.presentation.screens.details

import android.graphics.Color.parseColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.danik.tutuapp.R
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.ui.theme.*
import com.danik.tutuapp.util.Constants.ABOUT_TEXT_MAX_LINES
import com.danik.tutuapp.util.Constants.BASE_URL
import com.danik.tutuapp.util.Constants.MIN_HEIGHT_FRACTION_VALUE

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContent(
    navHostController: NavHostController,
    selectedTrain: Train?,
    colors: Map<String, String>,
) {

    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = Expanded)
    )


    val imageFraction = bottomSheetState.bottomSheetFraction

    val cornerShape by animateDpAsState(
        targetValue = if (imageFraction == 1f) PADDING_EXTRA_LARGE
        else 0.dp
    )

    var vibrantDarkColor by remember {
        mutableStateOf(value = "#ffffff")
    }
    var vibrantOnDarkColor by remember {
        mutableStateOf("#000000")
    }
    LaunchedEffect(key1 = selectedTrain) {


        vibrantDarkColor = colors["darkVibrant"] ?: "#ffffff"
        vibrantOnDarkColor = colors["onDarkVibrant"] ?: "#000000"

    }
    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = cornerShape, topEnd = cornerShape),
        sheetContent = {
            if (selectedTrain != null) {
                BottomSheetContent(
                    selectedTrain = selectedTrain,
                    contentColor = Color(parseColor(vibrantOnDarkColor)),
                    sheetBackgroundColor = Color(parseColor(vibrantDarkColor))
                )
            }
        },
        content = {
            if (selectedTrain != null) {
                BackgroundContent(
                    imageString = selectedTrain.image,
                    imageFraction = imageFraction,
                    backgroundColor = Color(parseColor(vibrantDarkColor))
                ) {
                    navHostController.popBackStack()
                }
            }
        },
        sheetPeekHeight = BOTTOM_SHEET_MIN_HEIGHT
    )
}

@Composable
fun BottomSheetContent(
    selectedTrain: Train,
    contentColor: Color = MaterialTheme.colors.titleColor,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = PADDING_LARGE)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    )
    {
        Text(
            text = selectedTrain.model,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h4.fontSize,
            color = contentColor
        )


        Text(
            modifier = Modifier.padding(bottom = PADDING_SMALL),
            text = stringResource(R.string.about_title),
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
        Text(
            modifier = Modifier
                .padding(bottom = PADDING_MEDIUM)
                .alpha(ContentAlpha.medium),
            text = selectedTrain.about,
            fontSize = MaterialTheme.typography.body1.fontSize,
            color = contentColor,
            maxLines = ABOUT_TEXT_MAX_LINES
        )

    }

}

@Composable
fun BackgroundContent(
    imageFraction: Float = 0.6f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    imageString: String,
    onCloseClicked: () -> Unit
) {
    val imageUrl = "$BASE_URL${imageString}"
    val painter =
        rememberAsyncImagePainter(
            model = imageUrl,
            onError = { R.drawable.ic_placeholder },
        )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .testTag("image")
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction + MIN_HEIGHT_FRACTION_VALUE)
                .align(alignment = Alignment.TopStart),
            painter = painter,
            contentDescription = stringResource(id = R.string.train_image),
            contentScale = ContentScale.Crop,

            )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier
                    .padding(all = PADDING_SMALL)
                    .size(INFO_BOX_ICON_SIZE),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.ic_close),
                    tint = Color.White
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.bottomSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress.fraction
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == Expanded && targetValue == Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed && targetValue == Expanded -> 1f - fraction
            currentValue == Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction
        }
    }

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedTrain = Train(
            id = 1,
            model = "Lightning ball",
            image = "/images/agniveena_express.jpg",
            about = "Some random text for testing"
        )
    )
}