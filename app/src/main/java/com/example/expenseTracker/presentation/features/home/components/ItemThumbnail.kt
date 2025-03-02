package com.example.expenseTracker.presentation.features.home.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.example.expenseTracker.utils.TestTags
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ItemThumbnail(thumbnailUrl: String) {
    GlideImage(
        imageModel = thumbnailUrl,
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
        contentDescription = TestTags.CAT_THUMBNAIL_PICTURE,
    )
}