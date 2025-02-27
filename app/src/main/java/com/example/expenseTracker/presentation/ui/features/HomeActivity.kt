package com.example.expenseTracker.presentation.ui.features

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expenseTracker.presentation.ui.navigation.CommonNavigationBar
import com.example.expenseTracker.presentation.ui.navigation.NavController
import com.example.expenseTracker.presentation.ui.navigation.NavigationScreens
import com.example.expenseTracker.presentation.ui.theme.ExpenseTrackerTheme
import com.example.expenseTracker.utils.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var localeHelper: LocaleHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        localeHelper.updateBaseContext(this.applicationContext)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val context = LocalContext.current

                val navController = rememberNavController()
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                val navigationSelectedItemState = MutableStateFlow(if(currentRoute == null || currentRoute == NavigationScreens.Home.screenRoute) 0 else 1)
                val navigationSelectedItem = navigationSelectedItemState.collectAsState()
                val hideBottomBarScreens =
                    listOf(
                        NavigationScreens.Home.screenRoute,
                        NavigationScreens.Profile.screenRoute
                    )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute in hideBottomBarScreens)
                            CommonNavigationBar(
                                context = context,
                                navigationSelectedItem = navigationSelectedItem.value,
                                navController = navController
                            ) {
                                navigationSelectedItemState.value = it
                            }
                    },
                ) { paddingValues ->
                    NavController(navController, paddingValues)
                }
            }
        }
    }
}
