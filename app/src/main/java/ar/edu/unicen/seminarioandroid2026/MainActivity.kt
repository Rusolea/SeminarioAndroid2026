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
import ar.edu.unicen.seminarioandroid2026.ui.main.MainScreen // 🟢 1. Agregamos el import de la nueva pantalla principal
import ar.edu.unicen.seminarioandroid2026.ui.popular.details.MovieDetailsScreen
import ar.edu.unicen.seminarioandroid2026.ui.theme.SeminarioAndroid2026Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeminarioAndroid2026Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Creamos el controlador de navegación
                    val navController = rememberNavController()

                    // 🟢 2. Definimos el nuevo grafo de navegación con "main" como inicio
                    NavHost(
                        navController = navController,
                        startDestination = "main" // 👈 Cambiado de "popular" a "main"
                    ) {
                        // Nueva Ruta Inicial: Contiene la barra de navegación inferior
                        composable("main") {
                            MainScreen(
                                onMovieClick = { movieId ->
                                    navController.navigate("details/$movieId")
                                }
                            )
                        }

                        // Ruta 2: Detalle de Película (se mantiene igual)
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