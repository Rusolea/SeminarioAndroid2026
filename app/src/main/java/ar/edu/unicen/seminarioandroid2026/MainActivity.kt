package ar.edu.unicen.seminarioandroid2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import ar.edu.unicen.seminarioandroid2026.ui.popular.PopularMoviesScreen
import ar.edu.unicen.seminarioandroid2026.ui.popular.details.MovieDetailsScreen
import ar.edu.unicen.seminarioandroid2026.ui.theme.SeminarioAndroid2026Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeminarioAndroid2026Theme  {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. Creamos el controlador de navegación
                    val navController = rememberNavController()

                    // 2. Definimos el grafo de navegación
                    NavHost(
                        navController = navController,
                        startDestination = "popular"
                    ) {
                        // Ruta 1: Lista de Películas Populares
                        composable("popular") {
                            PopularMoviesScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate("details/$movieId")
                                }
                            )
                        }

                        // Ruta 2: Detalle de Película (recibe movieId como parámetro)
                        composable(
                            route = "details/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") { type = NavType.IntType }
                            )
                        ) {
                            MovieDetailsScreen(
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}