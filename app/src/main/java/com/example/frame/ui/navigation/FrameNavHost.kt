package com.example.frame.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frame.ui.home.HomeScreen
import com.example.frame.ui.login.LoginScreen
import com.example.frame.ui.signup.SignUpScreen

@Composable
fun FrameNavHost(
    modifier: Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable(route = "login") {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate("signup") {
                        launchSingleTop = true
                    }
                },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = "signup") {
            SignUpScreen(
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = "home") {
            HomeScreen(
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("home") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
