package com.codmine.mukellef.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codmine.mukellef.presentation.components.ExitDialog
import com.codmine.mukellef.presentation.components.OfisScaffold
import com.codmine.mukellef.presentation.components.Screen
import com.codmine.mukellef.ui.theme.MobilOfisTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: MainViewModel = hiltViewModel()

            MobilOfisTheme {
                ProvideWindowInsets(
                    windowInsetsAnimationsEnabled = true,
                    consumeWindowInsets = false) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        val navController = rememberNavController()
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val context = LocalContext.current
                        val openExitAppDialog = viewModel.exitDialogState.value

                        OfisScaffold(
                            navController = navController,
                            showBars = shouldShowBars(navBackStackEntry),
                            modifier = Modifier.fillMaxSize(),
                            onActIconPressed = {
                                viewModel.onEvent(MainEvent.ExitDialog, context)
                            }
                        )
                        if (openExitAppDialog) {
                            ExitDialog(
                                onConfirmClick = {
                                    viewModel.onEvent(MainEvent.ExitConfirm, context)
                                },
                                onDismissClick = {
                                    viewModel.onEvent(MainEvent.ExitCancel, context)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun shouldShowBars(backStackEntry: NavBackStackEntry?): Boolean {
        return backStackEntry?.destination?.route in listOf(
            Screen.NotificationScreen.route,
            Screen.DocumentScreen.route,
            Screen.ChatPersonScreen.route,
            Screen.BalanceScreen.route
        )
    }
}