package com.danik.tutuapp.presentation.screens.main

import ShimmerEffect
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.danik.tutuapp.R
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.navigation.Screen
import com.danik.tutuapp.presentation.common.EmptyScreen
import com.danik.tutuapp.ui.theme.*
import com.danik.tutuapp.util.Constants.BASE_URL
import com.danik.tutuapp.util.TestTags.LAZY_COLUMN


@Composable
fun ListContent(trains: LazyPagingItems<Train>, navController: NavHostController) {

    val listState = handleListState(train = trains)

    if (listState) {
        LazyColumn(
modifier = Modifier.testTag(LAZY_COLUMN),
            contentPadding = PaddingValues(PADDING_SMALL),
            verticalArrangement = Arrangement.spacedBy(PADDING_SMALL)
        ) {
            items(items = trains, key = { train: Train ->
                train.id
            }) { train ->
                train?.let {
                    TrainItem(train = train, navController = navController)
                }

            }
        }
    }


}

@Composable
fun handleListState(train: LazyPagingItems<Train>): Boolean {

    train.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }

            error != null -> {
                EmptyScreen(error = error, trains = train)
                false
            }
            train.itemCount < 1 -> {
                EmptyScreen(trains = train)
                false
            }
            else -> {
                true
            }

        }
    }


}

@Composable
fun TrainItem(train: Train, navController: NavHostController) {


    Box(
        modifier = Modifier
            .height(TRAIN_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.Detail.passArgument(trainId = train.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(PADDING_LARGE)
        ) {

            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = "$BASE_URL${train.image}")
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "Train image"
            )

        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = 0.5f),
            shape = RoundedCornerShape(bottomStart = PADDING_LARGE, bottomEnd = PADDING_LARGE)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PADDING_MEDIUM)
            ) {
                Text(
                    text = train.model,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.h5.fontSize
                )
                Text(
                    text = train.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                )

            }

        }
    }
}

@Preview
@Composable
fun HeroItemPreview() {
    TrainItem(
        train = Train(
          id = 1,
            model = "Black Flash",
            image = "",
            about = "Some text about this train"
        ), navController = rememberNavController()
    )
}