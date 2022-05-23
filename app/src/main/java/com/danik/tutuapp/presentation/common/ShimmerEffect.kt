import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.danik.tutuapp.ui.theme.*

@Composable
fun ShimmerEffect() {
    LazyColumn(
        contentPadding = PaddingValues(PADDING_SMALL),
        verticalArrangement = Arrangement.spacedBy(PADDING_SMALL)
    ) {
        items(count = 2) {
            ShimmerItemAnimation()
        }
    }
}

@Composable
fun ShimmerItemAnimation() {
    val transition = rememberInfiniteTransition()
    val animatedAlpha by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    ShimmerItem(alpha = animatedAlpha)
}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha = alpha)
            .height(TRAIN_ITEM_HEIGHT),
        color = if (isSystemInDarkTheme()) Color.Black else ShimmerLightGray,
        shape = RoundedCornerShape(PADDING_LARGE)
    ) {
        Column(
            modifier = Modifier.padding(PADDING_MEDIUM),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .alpha(alpha = alpha)
                    .height(NAME_PLACEHOLDER_HEIGHT),
                color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray,
                shape = RoundedCornerShape(PADDING_LARGE)
            ) {}
            Spacer(modifier = Modifier.padding(PADDING_SMALL))

            repeat(3) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(alpha = alpha)
                        .height(BODY_PLACEHOLDER_HEIGHT),
                    color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(PADDING_LARGE)
                ) {}
                Spacer(modifier = Modifier.padding(PADDING_SMALLEST))
            }
            Row {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .size(RATING_PLACEHOLDER_HEIGHT)
                            .alpha(alpha = alpha),
                        color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray,
                        shape = RoundedCornerShape(PADDING_LARGE)
                    ) {}
                    Spacer(modifier = Modifier.padding(PADDING_SMALLEST))
                }
            }

        }

    }
}

@Preview
@Composable
fun ShimmerEffectPreview() {
    ShimmerItemAnimation()
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShimmerEffectNightPreview() {
    ShimmerItemAnimation()
}