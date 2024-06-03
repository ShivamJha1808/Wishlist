package com.example.wishlist

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun WishlistView()
{
    val viewModel: WishlistViewModel = viewModel()
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.HomeScreen.route)
    {
        composable(route=ScreenRoute.HomeScreen.route)
        {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            route= ScreenRoute.AddEditScreen.route+"/{id}",
            arguments = listOf(navArgument("id"){type= NavType.LongType})
            )
        {
            AddEditScreen(id = it.arguments?.getLong("id")?:0L, viewModel = viewModel, navController= navController)
        }
    }
}