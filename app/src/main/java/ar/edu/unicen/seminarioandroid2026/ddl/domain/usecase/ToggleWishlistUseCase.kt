package ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase

import ar.edu.unicen.seminarioandroid2026.ddl.data.repository.MovieRepository
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail // 🟢 Asegúrate de tener este import
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToggleWishlistUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    // 🟢 Función 1: Solo se encarga de consultar si es favorita
    fun isFavorite(movieId: Int): Flow<Boolean> {
        return repository.isMovieInWishlist(movieId) // 👈 Corregido: pasamos el ID y cerramos paréntesis
    }

    // 🟢 Función 2: Totalmente independiente, se encarga de agregar/quitar
    suspend fun toggle(movie: MovieDetail, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite) {
            repository.removeFromWishlist(movie.id)
        } else {
            repository.addToWishlist(movie)
        }
    }
}