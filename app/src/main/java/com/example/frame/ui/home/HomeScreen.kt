package com.example.frame.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    onLogoutClick: () -> Unit,
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState = homeViewModel.uiState

    LaunchedEffect(uiState.isSessionExpired) {
        if (uiState.isSessionExpired) {
            onLogoutClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Frame",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )

                TextButton(
                    onClick = { homeViewModel.loadProfile() }
                ) {
                    Text("Retry")
                }
            }

            uiState.profile != null -> {
                Text(text = uiState.profile.name)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = uiState.profile.email)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                homeViewModel.logout()
            },
            enabled = !uiState.isLoading
        ) {
            Text("Logout")
        }
    }
}
