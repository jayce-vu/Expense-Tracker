package com.example.expenseTracker.presentation.ui.features.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expenseTracker.R
import com.example.expenseTracker.domain.mappers.CatDataModel
import com.example.expenseTracker.presentation.contracts.BaseContract
import com.example.expenseTracker.presentation.contracts.CatContract
import com.example.expenseTracker.presentation.ui.components.CommonAppBar
import com.example.expenseTracker.presentation.ui.components.EmptyView
import com.example.expenseTracker.presentation.ui.components.LoadingBar
import com.example.expenseTracker.presentation.ui.features.home.navigation.NavigationScreens
import com.example.expenseTracker.presentation.ui.features.home.navigation.getBottomNavigationItems
import com.example.expenseTracker.presentation.ui.features.chooseLanguages.ChooseLanguages
import com.example.expenseTracker.presentation.ui.features.home.components.CatsList
import com.example.expenseTracker.presentation.ui.features.home.navigation.CommonNavigationBar
import com.example.expenseTracker.presentation.ui.features.home.navigation.NavController
import com.example.expenseTracker.presentation.ui.features.home.viewModel.HomeViewModel
import com.example.expenseTracker.presentation.ui.theme.Black80
import com.example.expenseTracker.presentation.ui.theme.ExpenseTrackerTheme
import com.example.expenseTracker.presentation.ui.theme.lightYellow
import com.example.expenseTracker.utils.TestTags
import com.example.expenseTracker.utils.TestTags.PROGRESS_BAR
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onRefreshCall: () -> Unit,
) {

    val context = LocalContext.current
    val navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val hideBottomBarScreens =
        listOf(NavigationScreens.Home.screenRoute, NavigationScreens.MyFavorites.screenRoute)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute in hideBottomBarScreens)
                CommonNavigationBar(context, navigationSelectedItem, navController)
        },
    ) { paddingValues ->
        NavController(navController, paddingValues)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserView(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    isFavCatsCall: Boolean,
    onNavigationRequested: (itemUrl: String, imageId: String) -> Unit,
    navController: NavHostController
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val catMessage = stringResource(R.string.cats_are_loaded)

    val state = viewModel.state.collectAsState().value
    val effectFlow = viewModel.effects.receiveAsFlow()
    LaunchedEffect(effectFlow) {
        effectFlow
            .onEach { effect ->
                if (effect is BaseContract.Effect.DataWasLoaded) {
                    snackBarHostState.showSnackbar(
                        message = catMessage,
                        duration = SnackbarDuration.Short,
                    )
                }
            }.collect { value ->
                if (value is BaseContract.Effect.Error) {
                    // Handle other emitted values if needed
                    snackBarHostState.showSnackbar(
                        message = "ERROR",
                        duration = SnackbarDuration.Short,
                    )
                }
            }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonAppBar(
                showRefreshButton = !isFavCatsCall,
                onNavigationIconClick = {
                },
                onRefreshCall = {
                    navController.navigate(NavigationScreens.Login.screenRoute)
                },
            )
        },
    ) { paddings ->
        Surface(
            modifier =
            Modifier.fillMaxSize().padding(paddings).semantics {
                testTag =
                    if (isFavCatsCall) TestTags.MY_FAVOURITE_SCREEN_TAG else TestTags.HOME_SCREEN_TAG
            },
        ) {
            Box {
                val cats = if (isFavCatsCall) state.favCatsList else state.cats
                if (isFavCatsCall && cats.isEmpty()) {
                    EmptyView(message = stringResource(R.string.favorite_screen_empty_list_text))
                } else {
                    CatsList(
                        cats = cats,
                        isLoading = state.isLoading,
                        isFavCatsCall = isFavCatsCall,
                    ) { itemUrl, imageId ->
                        onNavigationRequested(itemUrl, imageId)
                    }
                }
                if (state.isLoading) {
                    LoadingBar()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpenseTrackerTheme {
        HomeScreen(
            onRefreshCall = {},
        )
    }
}
