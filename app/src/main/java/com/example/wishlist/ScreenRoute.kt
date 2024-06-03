package com.example.wishlist

sealed class ScreenRoute
{
    object HomeScreen { const val route = "Home_screen"}
    object AddEditScreen { const val route = "Add_Edit_screen"}
}