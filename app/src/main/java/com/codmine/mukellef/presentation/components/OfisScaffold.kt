package com.codmine.mukellef.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.UiText
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfisScaffold(
    navController: NavController,
    modifier: Modifier,
    showNavigation: Boolean = false,
    showBars: Boolean = true,
    onActIconPressed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
             if(showBars) OfisTopBar(
                 navigation = {
                     if(showNavigation) {
                         IconButton(onClick = { navController.popBackStack() }) {
                             Icon(
                                 imageVector = Icons.Filled.ArrowBack,
                                 contentDescription = UiText.StringResources(R.string.top_bar_back_button_content_description).asString()
                             )
                         }
                     }
                 },
                 title = {
                     Text(
                         text = UiText.StringResources(R.string.app_name).asString(),
                         color = MaterialTheme.colorScheme.onSecondaryContainer,
                         style = MaterialTheme.typography.titleLarge,
                     )
                 },
                 actions = {
                     IconButton(
                         onClick = onActIconPressed
                     ) {
                         Icon(
                             imageVector = Icons.Filled.ExitToApp,
                             tint = MaterialTheme.colorScheme.onSecondaryContainer,
                             contentDescription = UiText.StringResources(R.string.exit_app).asString(),
                         )
                     }
                 }
             )
        },
        bottomBar = {
            if(showBars) OfisBottomBar(navController, showBars)
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        Navigation(navController, snackbarHostState, it)
    }
}