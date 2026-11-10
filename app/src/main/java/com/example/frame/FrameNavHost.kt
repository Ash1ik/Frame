package com.example.frame

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FrameNavHost (
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
                        popUpTo("login"){
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