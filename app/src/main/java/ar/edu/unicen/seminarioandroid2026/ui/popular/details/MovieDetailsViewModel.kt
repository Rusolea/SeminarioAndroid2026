package ar.edu.unicen.seminarioandroid2026.ui.popular.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // El nombre "movieId" debe ser exactamente igual al parámetro configurado en el NavHost
    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetails()
    }

    fun loadMovieDetails() {
        viewModelScope.launch {
            _uiState.value = MovieDetailsUiState.Loading

            getMovieDetailsUseCase(movieId)
                .onSuccess { detail ->
                    _uiState.value = MovieDetailsUiState.Success(detail)
                }
                .onFailure { error ->
                    val errorMessage = when (error) {
                        is java.net.UnknownHostException,
                        is java.io.IOException -> {
                            "Sin conexión a internet. Verifica tu red e intenta nuevamente."
                        }
                        is retrofit2.HttpException -> {
                            "Error en el servidor (${error.code()}). Intenta más tarde."
                        }
                        else -> {
                            error.localizedMessage ?: "Ocurrió un error inesperado"
                        }
                }


                    _uiState.value = MovieDetailsUiState.Error(errorMessage)
                }
        }
    }
}