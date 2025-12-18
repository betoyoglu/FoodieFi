package com.example.foodiefi.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodiefi.ui.components.FoodieFiBottomBar
import com.example.foodiefi.ui.detail.DetailScreen
import com.example.foodiefi.ui.favorite.FavoriteScreen
import com.example.foodiefi.ui.home.HomeScreen
import com.example.foodiefi.ui.home.ListScreen

@Composable
fun ScreenNav(){
    val navController = rememberNavController()

    //şu an hangi sayfada
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        bottomBar = {
            if(currentRoute == BottomNavItem.Home.route || currentRoute == BottomNavItem.Favorite.route){
                FoodieFiBottomBar(navController = navController)
            }
        }
    ){ innerPadding ->
        NavHost(
            navController= navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(BottomNavItem.Home.route){
                HomeScreen(navController= navController,viewModel = hiltViewModel())
            }
            composable(route = "detailScreen/{mealId}?time={time}&difficulty={difficulty}&rating={rating}",
                arguments = listOf(
                    navArgument("mealId"){type= NavType.StringType},
                    navArgument("time") { type = NavType.StringType
                        defaultValue = ""},
                    navArgument("difficulty") { type = NavType.StringType
                        defaultValue = ""},
                    navArgument("rating") { type = NavType.StringType
                        defaultValue = ""},
                )
            ){
                DetailScreen(navController = navController)
            }
            composable(BottomNavItem.Favorite.route){
                FavoriteScreen(navController = navController)
            }

            composable(
                route= "listScreen/{listType}",
                arguments = listOf(navArgument("listType"){type = NavType.StringType})
            ){backStackEntry ->
                val type = backStackEntry.arguments?.getString("listType") ?: "meals"

                ListScreen(
                    navController= navController,
                    listType = type
                )
            }
        }
    }
}