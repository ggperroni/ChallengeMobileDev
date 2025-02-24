package com.ggperroni.challengemobiledev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ggperroni.challengemobiledev.ui.theme.ChallengeMobileDevTheme
import com.ggperroni.challengemobiledev.view.components.SecurePreferences
import com.ggperroni.challengemobiledev.view.home.HomeScreen
import com.ggperroni.challengemobiledev.view.login.LoginScreen
import com.ggperroni.challengemobiledev.view.routes.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChallengeMobileDevTheme {
                val navController = rememberNavController()
                val sessionManager = SecurePreferences(this)

                val startDestination =
                    if (sessionManager.getAuthToken() != null && sessionManager.getUsername() != null) {
                        NavigationRoutes.HOME + "/${sessionManager.getUsername()}"
                    } else {
                        NavigationRoutes.LOGIN
                    }

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(NavigationRoutes.LOGIN) {
                        LoginScreen(
                            onNavigateToHome = { username ->
                                navController.navigate(NavigationRoutes.HOME + "/$username") {
                                    popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    composable(NavigationRoutes.HOME + "/{username}") { backStackEntry ->
                        val username = backStackEntry.arguments?.getString("username")
                        HomeScreen(
                            username = username!!,
                            onBackToLogin = {
                                sessionManager.clearAll()
                                navController.navigate(NavigationRoutes.LOGIN) {
                                    popUpTo(NavigationRoutes.HOME) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
