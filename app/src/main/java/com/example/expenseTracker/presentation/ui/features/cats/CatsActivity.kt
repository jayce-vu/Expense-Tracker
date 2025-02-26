package com.example.expenseTracker.presentation.ui.features.cats

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityOptionsCompat
import com.example.expenseTracker.presentation.ui.features.catDetails.CatFullImageActivity
import com.example.expenseTracker.presentation.ui.features.cats.view.CatScreen
import com.example.expenseTracker.presentation.ui.features.cats.viewModel.CatsViewModel
import com.example.expenseTracker.presentation.ui.theme.ExpenseTrackerTheme
import com.example.expenseTracker.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class CatsActivity : ComponentActivity() {
    private val viewModel: CatsViewModel by viewModels()
    private val myActivityResultContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.getFavCatsData()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                CatsDestination()
            }
        }
    }

    @Composable
    fun CatsDestination() {
        CatScreen(
            state = viewModel.state.collectAsState().value,
            effectFlow = viewModel.effects.receiveAsFlow(),
            onNavigationRequested = { itemUrl, imageId ->
                myActivityResultContract.launch(
                    Intent(
                        this@CatsActivity,
                        CatFullImageActivity::class.java,
                    ).apply {
                        putExtra(
                            Constants.URL,
                            itemUrl,
                        )
                        putExtra(Constants.IMAGE_ID, imageId)
                    },
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this),
                )
            },
            onRefreshCall = {
                viewModel.getCatsData()
            },
        )
    }
}
