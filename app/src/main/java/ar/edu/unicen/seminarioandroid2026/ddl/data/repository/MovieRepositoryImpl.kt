package ar.edu.unicen.seminarioandroid2026.ddl.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ar.edu.unicen.seminarioandroid2026.ddl.data.mapper.toDomain
import ar.edu.unicen.seminarioandroid2026.ddl.data.remote.TmdbApiService
import ar.edu.unicen.seminarioandroid2026.ddl.data.remote.paging.PopularMoviesPagingSource
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return runCatching {
            val response = apiService.getPopularMovies(page)
            response.results.map { it.toDomain() } }
    }

    override fun getPopularMoviesStream(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PopularMoviesPagingSource(apiService) }
        ).flow
    }



    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetail> {
        return runCatching { // esta linea runCatching sirve para manejar excepciones y errores
            val dto = apiService.getMovieDetails(movieId)
            dto.toDomain()
        }
    }

    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> {
        return runCatching {
            val response = apiService.searchMovies(query, page)
            response.results.map { dto -> dto.toDomain() }
        }
    }


}
//        return try {
//            val response = apiService.getMovieDetails(movieId)
//            // Asumiendo que tienes un mapper para MovieDetailDto o usas el mismo
//            // response.toDomain()
//            null // Implementar según tu MovieDetailDto
//        } catch (e: Exception) {
//            null
//        }

