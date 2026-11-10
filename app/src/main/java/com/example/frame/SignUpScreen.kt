package com.example.frame

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignUpScreen(onLoginClick: () -> Unit) {

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var submitted by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

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

                    Toast.makeText(
                        context, "Validation Passed", Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {
            Text(
                text = "Create Account"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onLoginClick) {
            Text("Already have an account? Sign in")
        }
    }
}

@Composable
fun FrameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        singleLine = true,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(errorMessage)
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun FramePasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        trailingIcon = {
            TextButton(
                onClick = {
                    passwordVisible = !passwordVisible
                }
            ) {
                if (!passwordVisible) {
                    Text(text = "Show")
                } else {
                    Text(text = "Hide")
                }
            }
        },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(errorMessage)
            }
        },
        modifier = modifier.fillMaxWidth()
    )

}