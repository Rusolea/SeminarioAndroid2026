package ar.edu.unicen.seminarioandroid2026.ddl.data.repository

import androidx.paging.PagingData
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    /**
     * Obtiene la lista de películas populares convertidas al modelo de dominio.
     */

    suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>>

    // 🟢 Nuevo flujo paginado
     fun getPopularMoviesStream(): Flow<PagingData<Movie>>


    /**
     * Obtiene el detalle de una película específica.
     */

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetail>

    suspend fun searchMovies(query: String, page: Int = 1): Result<List<Movie>>

    // 🟢 Persistencia Local (Room)
    fun getWishlistMovies(): Flow<List<Movie>>
    fun isMovieInWishlist(movieId: Int) : Flow<Boolean>
    suspend fun addToWishlist(movieDetail: MovieDetail)
    suspend fun removeFromWishlist(movieId: Int)

}