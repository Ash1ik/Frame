package com.example.frame.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frame.ui.components.FramePasswordField
import com.example.frame.ui.components.FrameTextField

@Composable
fun SignUpScreen(
    onLoginClick: () -> Unit,
    signupViewModel: SignupViewModel = viewModel()
) {
    val uiState = signupViewModel.uiState

    LaunchedEffect(uiState.isSignupSuccessful) {
        if (uiState.isSignupSuccessful) {
            onLoginClick()
        }
    }

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var submitted by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        FrameTextField(
            value = name,
            onValueChange = { name = it },
            label = "Name",
            modifier = Modifier,
            errorMessage = if (submitted && name.isBlank()) "Name is Required" else null
        )

        Spacer(modifier = Modifier.height(16.dp))

        FrameTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            modifier = Modifier,
            errorMessage = if (submitted && email.isBlank()) "Email is Required" else null
        )

        Spacer(modifier = Modifier.height(16.dp))

        FramePasswordField(
            value = password,
            onValueChange = {
                password = it
            },
            label = "Password",
            modifier = Modifier,
            errorMessage = if (submitted && password.isBlank()) "Password is Required" else null
        )

        Spacer(modifier = Modifier.height(16.dp))

        FramePasswordField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            label = "Confirm Password",
            modifier = Modifier,
            errorMessage = when {
                !submitted -> null
                confirmPassword.isBlank() -> "Confirm password is required"
                password != confirmPassword -> "Passwords do not match"
                else -> null
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                submitted = true

                val isFormValid =
                    name.isNotBlank() &&
                            email.isNotBlank() &&
                            password.isNotBlank() &&
                            confirmPassword.isNotBlank() &&
                            password == confirmPassword

                if (isFormValid) {
                    signupViewModel.signup(name, email, password)
                }
            },
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Create Account")
            }
        }

        uiState.errorMessage?.let { message ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onLoginClick,
            enabled = !uiState.isLoading
        ) {
            Text("Already have an account? Sign in")
        }
    }
}
