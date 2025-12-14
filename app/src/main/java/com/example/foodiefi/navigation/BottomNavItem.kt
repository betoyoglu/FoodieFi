package com.example.foodiefi.navigation

import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem (
    val route: String,
    val title: String,
    val icon: ImageVector,
    val unselectedIcon: ImageVector
){
    object Home: BottomNavItem("home", "Home", Icons.Default.Home, Icons.Default.Home)
    object Favorite: BottomNavItem("favoriteScreen", "Favorites", Icons.Default.Favorite,
        Icons.Default.FavoriteBorder)
}