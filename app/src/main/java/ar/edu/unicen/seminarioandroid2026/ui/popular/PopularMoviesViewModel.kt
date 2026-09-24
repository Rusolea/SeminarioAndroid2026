package ar.edu.unicen.seminarioandroid2026.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ar.edu.unicen.seminarioandroid2026.R
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase.GetPopularMoviesPagingUseCase
// 1. Asegúrate de que este import coincida con la ubicación de tu UseCase
import ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase.GetPopularMoviesUseCase
import ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    // UseCase para obtener la lista simple (usado en fetchPopularMovies)
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,

    // 🛠️ AGREGAR ESTE: UseCase para el flujo de Paging 3
    private val getPopularMoviesPagingUseCase: GetPopularMoviesPagingUseCase,

    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    // 🟢 Ahora sí coincide el nombre con el parámetro del constructor
    val popularMoviesPagingFlow: Flow<PagingData<Movie>> =
        getPopularMoviesPagingUseCase()
            .cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow<PopularMoviesUiState>(PopularMoviesUiState.Loading)
    val uiState: StateFlow<PopularMoviesUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchPopularMovies()
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        searchJob?.cancel() // Cancelamos la búsqueda anterior

        if (newQuery.isBlank()) {
            fetchPopularMovies() // Si el texto queda vacío, recarga populares

            }else
                searchJob = viewModelScope.launch {
                    delay(400) // Pequeña pausa (debounce) para esperar a que el usuario termine de escribir
                    _uiState.value = PopularMoviesUiState.Loading

                    searchMoviesUseCase(newQuery)
                        .onSuccess { movies ->
                            if (movies.isEmpty()) {
                                _uiState.value = PopularMoviesUiState.Empty
                            } else {
                                _uiState.value = PopularMoviesUiState.Success(movies)
                            }
                }.onFailure { error ->
                    val errorResId = when (error) {
                        is UnknownHostException, is IOException -> R.string.error_no_internet
                        else -> R.string.error_server
                    }
                    _uiState.value = PopularMoviesUiState.Failure(errorResId)
                }
                    }


    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            _uiState.value = PopularMoviesUiState.Loading

            // 🟢 Ahora manejamos el Result directamente con onSuccess y onFailure
            getPopularMoviesUseCase()
                .onSuccess { movies ->
                    if (movies.isEmpty()) {
                        _uiState.value = PopularMoviesUiState.Empty
                    } else {
                        _uiState.value = PopularMoviesUiState.Success(movies)
                    }
                }
                .onFailure { exception ->
                    // 🛠️ Captura de excepciones de red integrada
                    val errorResId = when (exception) {
                        is java.net.UnknownHostException,
                        is java.io.IOException -> R.string.error_no_internet
                        else -> R.string.error_server
                    }
                    _uiState.value = PopularMoviesUiState.Failure(errorResId)
                }
        }
    }
}
// Representación de los diferentes estados de la pantalla
//sealed class PopularMoviesUiState {
//    object Loading : PopularMoviesUiState()
//    data class Success(val movies: List<Movie>) : PopularMoviesUiState()
//    data class Error(val message: String) : PopularMoviesUiState()
//    object Empty : PopularMoviesUiState()
//}