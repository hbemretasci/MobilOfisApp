package com.codmine.mukellef.presentation.balance_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.codmine.mukellef.domain.model.balance.Transaction
import com.codmine.mukellef.presentation.components.GlowIndicator
import com.codmine.mukellef.presentation.components.ReLoadData
import com.codmine.mukellef.ui.theme.spacing
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun BalanceScreen(
    paddingValues: PaddingValues,
    viewModel: BalanceViewModel = hiltViewModel()
) {
    val state = viewModel.dataState.value
    val context = LocalContext.current
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(BalanceEvent.LoadData, context)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.onEvent(BalanceEvent.Refresh, context) },
            indicator = { state, trigger ->
                GlowIndicator(
                    swipeRefreshState = state,
                    refreshTriggerDistance = trigger
                )
            }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.large)) }
                items(state.transactions) { transaction ->
                    TransactionItem(transaction = transaction)
                }
            }
        }
        if(state.error.isNotBlank()) {
            ReLoadData(
                modifier = Modifier.fillMaxSize(),
                errorMsg = state.error,
                onRetry = { viewModel.onEvent(BalanceEvent.LoadData, context) }
            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.small
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.medium
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = transaction.dateTime,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = MaterialTheme.spacing.medium,
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = transaction.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = transaction.amount,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}