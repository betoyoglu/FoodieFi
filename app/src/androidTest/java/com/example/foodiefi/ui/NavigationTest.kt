package com.example.foodiefi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import com.example.foodiefi.ui.components.RecipeFoodCard
import com.example.foodiefi.ui.components.SeeAllCard
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickingSeeAll_navigateTo_ListScreen(){
        //sanal nav kontrolcüsü
        lateinit var navController : TestNavHostController

        composeTestRule.setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())

                val navGraph = navController.createGraph(startDestination = "home"){
                    composable("home"){
                        SeeAllCard(
                            modifier = Modifier.testTag("my_see_all_button"),
                            onClick = { navController.navigate("listScreen/meals")}
                        )
                    }
                    composable("listScreen/{type}"){

                    }
                }

                navController.graph = navGraph
            }

        }

        //butona tıkla
        composeTestRule.onNodeWithTag("my_see_all_button").performClick()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals("listScreen/{type}", route)
    }

    @Test
    fun clickingFoodCard_navigatesTo_DetailScreen_withArguments() {
        lateinit var navController: TestNavHostController

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            val navGraph = navController.createGraph(startDestination = "home") {
                composable("home") {
                    RecipeFoodCard(
                        foodName = "Test Pasta",
                        foodDescription = "Delicious Test",
                        foodImage = "",
                        onClick = {
                            navController.navigate(
                                "detailScreen/999?time=30min&difficulty=Easy&rating=5.0"
                            )
                        }
                    )
                }
                composable(
                    route = "detailScreen/{mealId}?time={time}&difficulty={difficulty}&rating={rating}"
                ) {
                }
            }
            navController.graph = navGraph
        }

        composeTestRule.onNodeWithText("Test Pasta").performClick()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals("detailScreen/{mealId}?time={time}&difficulty={difficulty}&rating={rating}", currentRoute)

        val args = navController.currentBackStackEntry?.arguments
        assertEquals("999", args?.getString("mealId"))
        assertEquals("30min", args?.getString("time"))
        assertEquals("Easy", args?.getString("difficulty"))
    }
}