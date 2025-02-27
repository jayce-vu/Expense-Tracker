package com.example.expenseTracker.presentation.ui.features.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.example.expenseTracker.R
import com.example.expenseTracker.domain.mappers.CatDataModel
import com.example.expenseTracker.utils.TestTags

@Composable
fun CatsList(
    isLoading: Boolean = false,
    cats: List<CatDataModel>,
    isFavCatsCall: Boolean,
    onItemClicked: (url: String, imageId: String) -> Unit = { _: String, _: String -> },
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        content = {
            this.items(cats) { item ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 6.dp,
                    ),
                    border = BorderStroke(0.5.dp, Color.Gray),
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                        .clickable {
                            if (!isLoading) {
                                onItemClicked(item.url, item.imageId)
                            }
                        }
                        .semantics { testTag = TestTags.CAT_ITEM_TAG },
                ) {
                    Box(
                        modifier =
                        Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .padding(top = 1.dp),
                    ) {
                        ItemThumbnail(thumbnailUrl = item.url)
                        if (!isFavCatsCall && !item.name.isNullOrBlank()) {
                            Column(
                                modifier =
                                Modifier
                                    .background(Color.Gray)
                                    .fillMaxWidth()
                                    .align(Alignment.BottomStart),
                            ) {
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    color = colorResource(id = R.color.colorPrimary),
                                )
                                item.origin?.let {
                                    Text(
                                        text = it,
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        color = colorResource(id = R.color.colorPrimary),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        modifier =
        Modifier
            .semantics { testTag = TestTags.CATS_LIST_TAG }
            .fillMaxSize(),
    )
}
