package com.example.cineswipe.ui.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cineswipe.R

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Swipe,
        modifier = modifier,
    ) {
        composable<Routes.Swipe> {
            PlaceholderScreen(stringResource(R.string.tab_swipe)) {
                // Временная кнопка для проверки перехода. Удалить на этапе 7.
                Button(onClick = { navController.navigate(Routes.Details(movieId = 1)) }) {
                    Text("Открыть детали (тест)")
                }
            }
        }
        composable<Routes.Collections> { PlaceholderScreen(stringResource(R.string.tab_collections)) }
        composable<Routes.Lists> { PlaceholderScreen(stringResource(R.string.tab_lists)) }
        composable<Routes.More> { PlaceholderScreen(stringResource(R.string.tab_more)) }

        composable<Routes.Details> { entry ->
            val args = entry.toRoute<Routes.Details>()
            DetailsPlaceholder(
                movieId = args.movieId,
                onBack = { navController.popBackStack() },
            )
        }
    }
}