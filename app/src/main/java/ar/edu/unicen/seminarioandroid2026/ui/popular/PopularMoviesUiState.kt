package ar.edu.unicen.seminarioandroid2026.ui.popular

import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie

sealed class PopularMoviesUiState {
    data object Loading : PopularMoviesUiState()
    data class Success(val movies: List<Movie>) : PopularMoviesUiState()
    // Cambiamos Error por Failure o MovieError
    data class Failure(val messageResId: Int) : PopularMoviesUiState()
    data object Empty : PopularMoviesUiState()
}