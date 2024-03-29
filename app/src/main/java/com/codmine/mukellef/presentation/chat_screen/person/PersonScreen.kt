package com.codmine.mukellef.presentation.chat_screen.person

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.tax_payer.RelatedUser
import com.codmine.mukellef.domain.util.Constants.ROUNDED_VALUE
import com.codmine.mukellef.presentation.chat_screen.person.components.ReadMessages
import com.codmine.mukellef.presentation.chat_screen.person.components.UnReadMessages
import com.codmine.mukellef.presentation.components.GlowIndicator
import com.codmine.mukellef.presentation.components.ReLoadData
import com.codmine.mukellef.presentation.components.Screen
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.presentation.components.DataNotFound
import com.codmine.mukellef.ui.theme.spacing
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PersonScreen(
    open: (String) -> Unit,
    paddingValues: PaddingValues,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isRefreshing
    )

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(PersonEvent.LoadData)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(PersonEvent.Refresh) },
            indicator = { state, trigger ->
                GlowIndicator(
                    swipeRefreshState = state,
                    refreshTriggerDistance = trigger
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.large)) }
                items(uiState.relatedUsers) { user ->
                    UserItem(
                        user = user,
                        unRead = user.unReadCount,
                        onItemClick = { clickedUser ->
                            val opponentId = clickedUser.id
                            val opponentName = clickedUser.name
                            open(Screen.ChatMessageScreen.route + "/${opponentId}/${opponentName}")
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.large)) }
            }
        }
        if(uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if(uiState.errorStatus) {
            ReLoadData(
                modifier = Modifier.fillMaxSize(),
                errorMsg = uiState.errorText ?: UiText.StringResources(R.string.unexpected_error),
                onRetry = { viewModel.onEvent(PersonEvent.Refresh) }
            )
        }
        if((!uiState.isLoading) && (!uiState.errorStatus) && (uiState.relatedUsers.isEmpty())) {
            DataNotFound(message = UiText.StringResources(R.string.person_not_found))
        }
    }
}

@Composable
fun UserItem(
    user: RelatedUser,
    unRead: String,
    onItemClick: (RelatedUser) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.small
            )
            .clickable { onItemClick(user) },
        shape = RoundedCornerShape(ROUNDED_VALUE.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val messageCount = unRead.toInt()
            if(messageCount > 99) UnReadMessages("99+")
                else if (messageCount > 0) UnReadMessages(user.unReadCount)
                    else ReadMessages()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = user.eMail,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}