package com.example.expenseTracker.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.expenseTracker.utils.TestTags
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CircleAvatar(imageUrl: String, size: Dp = 80.dp) {

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White)
            .border(2.dp, Color.White, CircleShape),
    ) {
        GlideImage(
            imageModel = imageUrl,
            modifier =
            Modifier
                .semantics { testTag = TestTags.LIST_IMG }
                .wrapContentSize()
                .wrapContentHeight()
                .fillMaxWidth(),
            // shows a progress indicator when loading an image.
            contentScale = ContentScale.Crop,
            circularReveal = CircularReveal(duration = 100),
            shimmerParams =
            ShimmerParams(
                baseColor = MaterialTheme.colorScheme.background,
                highlightColor = Color.Gray,
                durationMillis = 500,
                dropOff = 0.55f,
                tilt = 20f,
            ),
            failure = {
                Image(imageVector = Icons.Filled.Close, null)
            },
            contentDescription = TestTags.CAT_THUMBNAIL_PICTURE,
        )
    }
}