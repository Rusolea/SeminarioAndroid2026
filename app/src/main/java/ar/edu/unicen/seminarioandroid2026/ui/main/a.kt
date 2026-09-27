package ar.edu.unicen.seminarioandroid2026.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ar.edu.unicen.seminarioandroid2026.ui.popular.PopularMoviesScreen
import ar.edu.unicen.seminarioandroid2026.ui.wishlist.WishlistScreen

// ⚠️ Nota: Asegúrate de que estos imports coincidan con la ubicación real de tus pantallas
// import ar.edu.unicen.seminarioandroid2026.ui.popular.PopularMoviesScreen
// import ar.edu.unicen.seminarioandroid2026.ui.wishlist.WishlistScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onMovieClick: (Int) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Star, contentDescription = null) },
                    label = { Text("Populares") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                    label = { Text("Deseados") }
                )
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> PopularMoviesScreen(onMovieClick = onMovieClick)
                1 -> WishlistScreen(onMovieClick = onMovieClick)
            }
        }
    }
}