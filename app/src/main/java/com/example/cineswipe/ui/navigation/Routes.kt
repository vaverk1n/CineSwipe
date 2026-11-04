package com.example.cineswipe.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cineswipe.R
import kotlinx.serialization.Serializable


sealed interface Routes {
    sealed interface Tab : Routes

    @Serializable data object Swipe : Tab
    @Serializable data object Collections : Tab
    @Serializable data object Lists : Tab
    @Serializable data object More  : Tab
    @Serializable data class Details(val movieId: Int) : Routes
}

enum class BottomTab(
    val route: Routes.Tab,
    @StringRes val label: Int,
    val icon: ImageVector,
) {
    Swipe(Routes.Swipe, R.string.tab_swipe, Icons.Filled.Favorite),
    Collections(Routes.Collections, R.string.tab_collections, Icons.Filled.Search),
    Lists(Routes.Lists, R.string.tab_lists, Icons.AutoMirrored.Filled.List),
    More(Routes.More, R.string.tab_more, Icons.Filled.Settings),
}