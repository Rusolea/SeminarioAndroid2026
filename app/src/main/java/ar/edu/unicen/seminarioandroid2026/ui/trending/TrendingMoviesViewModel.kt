package ar.edu.unicen.seminarioandroid2026.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminarioandroid2026.R
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject


    sealed interface TrendingMoviesUiState{
        data object Loading : TrendingMoviesUiState
        data class Success(val movies: List<Movie>) : TrendingMoviesUiState
        data object Empty : TrendingMoviesUiState
        data class Failure(val messageResId: Int) : TrendingMoviesUiState
    }

    @HiltViewModel
    class TrendingMoviesViewModel @Inject constructor(
        private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<TrendingMoviesUiState>(TrendingMoviesUiState.Loading)
        val uiState: StateFlow<TrendingMoviesUiState> =_uiState.asStateFlow()

        init {
            fetchTrendingMovies()
        }

        fun fetchTrendingMovies() {
            viewModelScope.launch {
                _uiState.value = TrendingMoviesUiState.Loading
                getTrendingMoviesUseCase() .onSuccess { movies ->
                     if (movies.isEmpty()) {
                        _uiState.value = TrendingMoviesUiState.Empty
                    } else { _uiState.value = TrendingMoviesUiState.Success(movies) }
                }
                    .onFailure { error ->
                        val errorResId = when (error) {
                            is UnknownHostException, is IOException -> R.string.error_no_internet else -> R.string.error_server }
                        _uiState.value = TrendingMoviesUiState.Failure(errorResId) }

            }
        }
    }
