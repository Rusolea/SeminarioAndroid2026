package ar.edu.unicen.seminarioandroid2026.ui.popular.details

import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail

interface MovieDetailsUiState {
    data object Loading : MovieDetailsUiState
    data class Success(val movieDetail: MovieDetail) : MovieDetailsUiState
    data class Error(val message: String) : MovieDetailsUiState
}