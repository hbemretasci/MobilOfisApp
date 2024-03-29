package com.codmine.mukellef.presentation.login_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.R
import com.codmine.mukellef.presentation.components.Screen
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.ui.theme.spacing

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    openAndPopUp: (Screen, Screen) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when(event) {
                is LoginUiEvent.ValidationSuccess -> {
                    viewModel.onEvent(LoginEvent.CheckLoginApi)
                }
                is LoginUiEvent.LoginSuccessApi -> {
                    viewModel.onEvent(LoginEvent.CheckLoginDatabase)
                }
                is LoginUiEvent.LoginSuccessDatabase -> {
                    openAndPopUp(Screen.NotificationScreen, Screen.LoginScreen)
                }
                is LoginUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message.asString(context))
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.extraLarge),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = MaterialTheme.spacing.medium),
                text = UiText.StringResources(R.string.app_name).asString(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxLarge))
            Text(
                modifier = Modifier.padding(start = MaterialTheme.spacing.medium),
                text = UiText.StringResources(R.string.login_screen_text).asString(),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            TextField(
                value = uiState.gib,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.GibChanged(it))
                },
                isError = uiState.gibError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(UiText.StringResources(R.string.gib_place_holder).asString())
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
                maxLines = 1,
                singleLine = true
            )
            if (uiState.gibError != null) {
                Text(
                    text = uiState.gibError?.asString() ?: UiText.StringResources(R.string.gib_error).asString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            TextField(
                value = uiState.vk,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.VkChanged(it))
                },
                isError = uiState.vkError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(UiText.StringResources(R.string.vk_place_holder).asString())
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
                maxLines = 1,
                singleLine = true
            )
            if (uiState.vkError != null) {
                Text(
                    text = uiState.vkError?.asString() ?: UiText.StringResources(R.string.vk_error).asString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            TextField(
                value = uiState.password,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.PasswordChanged(it))
                },
                isError = uiState.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(UiText.StringResources(R.string.password_place_holder).asString())
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.onEvent(LoginEvent.Validate)
                    }
                ),
                maxLines = 1,
                singleLine = true
            )
            if (uiState.passwordError != null) {
                Text(
                    text = uiState.passwordError?.asString() ?: UiText.StringResources(R.string.password_error).asString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            Button(
                onClick = {
                    keyboardController?.hide()
                    viewModel.onEvent(LoginEvent.Validate)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = MaterialTheme.spacing.medium)
            ) {
                Text(
                    text = UiText.StringResources(R.string.login_button_text).asString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        if(uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}