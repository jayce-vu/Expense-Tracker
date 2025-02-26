package com.example.expenseTracker.presentation.ui.features.catDetails

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.expenseTracker.presentation.ui.features.catDetails.view.CatFullDetail
import com.example.expenseTracker.presentation.ui.features.catDetails.viewModel.CatsDetailsViewModel
import com.example.expenseTracker.presentation.ui.theme.ExpenseTrackerTheme
import com.example.expenseTracker.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatFullImageActivity : ComponentActivity() {
    private var initialState: Boolean = false
    private val viewModel: CatsDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(Constants.IMAGE_ID)?.let { viewModel.checkFav(it) }
        setContent {
            ExpenseTrackerTheme {
                CatsFullView()
            }
        }
    }

    @Composable
    private fun CatsFullView() {
        initialState = remember { viewModel.isFavourite.value }
        val isFavourite by viewModel.isFavourite.collectAsState()
        CatFullDetail(
            url = intent.getStringExtra(Constants.URL),
            onBackPressed = {
                if (initialState != isFavourite) {
                    val resultIntent = Intent()
                    resultIntent.putExtra(Constants.FAV_ID, viewModel.favId)
                    setResult(RESULT_OK, resultIntent)
                }
                onBackPressedDispatcher.onBackPressed()
            },
            isFavourite = isFavourite,
            favSelection = {
                viewModel.updateFavouriteState(it)
                if (it) {
                    viewModel.postFavCatData()
                } else {
                    viewModel.deleteFavCatData()
                }
            },
        )
    }
}
