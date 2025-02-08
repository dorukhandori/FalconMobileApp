package com.example.b2becommerce.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.b2becommerce.presentation.auth.login.LoginScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ProductList : Screen("product_list")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart")
    object Profile : Screen("profile")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            // RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            // HomeScreen(navController)
        }
        composable(Screen.ProductList.route) {
            // ProductListScreen(navController)
        }
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            // ProductDetailScreen(navController, productId)
        }
        composable(Screen.Cart.route) {
            // CartScreen(navController)
        }
        composable(Screen.Profile.route) {
            // ProfileScreen(navController)
        }
    }
} 