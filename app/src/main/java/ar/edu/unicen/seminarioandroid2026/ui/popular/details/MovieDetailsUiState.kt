package ar.edu.unicen.seminarioandroid2026.ui.popular.details

import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail

sealed interface MovieDetailsUiState {
    data object Loading : MovieDetailsUiState

    data class Success(
        val movieDetail: MovieDetail,
        val similarMovies: List<Movie>,
        val recommendedMovies: List<Movie>

    ) : MovieDetailsUiState
    data class Error(val message: String) : MovieDetailsUiState
}