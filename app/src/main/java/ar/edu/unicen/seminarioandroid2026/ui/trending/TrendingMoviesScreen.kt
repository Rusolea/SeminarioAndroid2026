import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unicen.seminarioandroid2026.R
import ar.edu.unicen.seminarioandroid2026.ui.popular.ErrorMoviesView
import ar.edu.unicen.seminarioandroid2026.ui.popular.MovieGrid
import ar.edu.unicen.seminarioandroid2026.ui.trending.TrendingMoviesUiState
import ar.edu.unicen.seminarioandroid2026.ui.trending.TrendingMoviesViewModel

// 🟢 Importa los componentes compartidos (ajusta el paquete si es necesario)
@Composable
fun TrendingMoviesScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: TrendingMoviesViewModel = hiltViewModel()
) {
    // 🟢 Corregido: Accedemos a 'uiState' (público), no a '_uiState' (privado)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = uiState) {
                is TrendingMoviesUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is TrendingMoviesUiState.Failure -> {
                    ErrorMoviesView(
                        message = stringResource(id = state.messageResId),
                        onRetry = { viewModel.fetchTrendingMovies() },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is TrendingMoviesUiState.Success -> {
                    MovieGrid(
                        movies = state.movies,
                        onMovieClick = onMovieClick
                    )
                }

                is TrendingMoviesUiState.Empty -> {
                    Text(
                        text = stringResource(id = R.string.no_movies_found),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}