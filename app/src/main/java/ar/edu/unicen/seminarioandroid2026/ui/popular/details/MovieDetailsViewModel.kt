package ar.edu.unicen.seminarioandroid2026.ui.popular.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail
import ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase.GetMovieDetailsUseCase
import ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase.ToggleWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // El nombre "movieId" debe ser exactamente igual al parámetro configurado en el NavHost
    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    // Observamos el flujo y lo convertimos en un estado que Compose entienda
    val isFavorite: StateFlow<Boolean> = toggleWishlistUseCase.isFavorite(movieId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

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

    //Reactividad: Al usar stateIn, si la película se agrega a favoritos desde cualquier otra parte de la app, el isFavorite del ViewModel se actualizará solo.
    //Simplicidad: La función toggleWishlist ya no tiene que ir a preguntar a la base de datos; simplemente mira el valor que ya tiene guardado en isFavorite.value.
    //Limpieza: Evitas errores de tipos y paréntesis mal cerrados.
    //Nota: Asegúrate de importar kotlinx.coroutines.flow.SharingStarted y kotlinx.coroutines.flow.stateIn.

    fun toggleWishlist(movie: MovieDetail) {
        viewModelScope.launch {
            // Usamos el valor actual de nuestro StateFlow
            toggleWishlistUseCase.toggle(movie, isFavorite.value)
        }
    }
}