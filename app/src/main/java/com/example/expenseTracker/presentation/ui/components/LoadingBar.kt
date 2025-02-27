package com.example.expenseTracker.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.example.expenseTracker.R
import com.example.expenseTracker.presentation.ui.theme.lightYellow
import com.example.expenseTracker.utils.TestTags
import com.example.expenseTracker.utils.TestTags.PROGRESS_BAR

@Composable
fun LoadingBar() {
    Box(
        modifier =
        Modifier
            .semantics { testTag = TestTags.LOADING_BAR_TAG }
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier =
            Modifier
                .testTag(PROGRESS_BAR)
                .size(60.dp),
            color = colorResource(id = R.color.colorPrimary),
            strokeWidth = 5.dp, // Width of the progress indicator's stroke
            trackColor = lightYellow, // Color of the track behind the progress indicator
            strokeCap = StrokeCap.Round,
        )
    }
}