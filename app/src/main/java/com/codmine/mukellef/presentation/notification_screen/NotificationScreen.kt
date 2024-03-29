package com.codmine.mukellef.presentation.notification_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.notifications.Notification
import com.codmine.mukellef.domain.util.Constants.ROUNDED_VALUE
import com.codmine.mukellef.presentation.components.DataNotFound
import com.codmine.mukellef.presentation.components.GlowIndicator
import com.codmine.mukellef.presentation.components.ReLoadData
import com.codmine.mukellef.presentation.notification_screen.components.NotificationDocumentIcon
import com.codmine.mukellef.presentation.notification_screen.components.ReadNotificationIcon
import com.codmine.mukellef.presentation.notification_screen.components.UnReadNotificationIcon
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.ui.theme.spacing
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun NotificationScreen(
    paddingValues: PaddingValues,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isRefreshing
    )

    var expandedNotification by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(NotificationEvent.LoadData)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.onEvent(NotificationEvent.Refresh) },
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
                items(uiState.notifications) { notification ->
                    NotificationItem(
                        notification = notification,
                        expanded = expandedNotification == notification.id,
                        onItemClick = {
                            expandedNotification = if (expandedNotification == it.id) null else it.id
                            if (it.readingTime.isEmpty()) viewModel.onEvent(NotificationEvent.ReadNotification(it))
                        },
                        onDocumentClick = {
                            viewModel.onEvent(NotificationEvent.ShowNotification(it))
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
                onRetry = { viewModel.onEvent(NotificationEvent.Refresh) }
            )
        }
        if((!uiState.isLoading) && (!uiState.errorStatus) && (uiState.notifications.isEmpty())) {
            DataNotFound(message = UiText.StringResources(R.string.notification_not_found))
        }
    }
}

@Composable
fun NotificationItem(
    notification: Notification,
    expanded: Boolean,
    onItemClick: (Notification) -> Unit,
    onDocumentClick: (Notification) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.extraSmall,
            )
            .clickable { onItemClick(notification) },
        shape = RoundedCornerShape(ROUNDED_VALUE.dp)
    ) {
        if (!expanded) {
            Row (
                modifier = Modifier.padding(MaterialTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                if (notification.readingTime.isNotEmpty()) ReadNotificationIcon() else UnReadNotificationIcon()
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Text(
                    text = notification.message,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (notification.readingTime.isEmpty()) FontWeight.Bold else FontWeight.Normal,
                    color = if (notification.readingTime.isEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Column (
                horizontalAlignment = Alignment.End
            ) {
                Row (
                    modifier = Modifier.padding(MaterialTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                    ReadNotificationIcon()
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                    Text(
                        text = notification.message,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (notification.documentName != "") {
                    IconButton(
                        onClick = { onDocumentClick(notification) }
                    ) {
                        NotificationDocumentIcon(
                            modifier = Modifier
                                .padding(
                                    bottom = MaterialTheme.spacing.medium,
                                    end = MaterialTheme.spacing.medium
                                )
                                .scale(1.5f, 1.5f)
                        )
                    }
                }
            }
        }
    }
}