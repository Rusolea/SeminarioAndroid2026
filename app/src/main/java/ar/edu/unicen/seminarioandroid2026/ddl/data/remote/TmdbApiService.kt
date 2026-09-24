package ar.edu.unicen.seminarioandroid2026.ddl.data.remote

import ar.edu.unicen.seminarioandroid2026.ddl.data.remote.dto.MovieDetailDto
import ar.edu.unicen.seminarioandroid2026.ddl.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TmdbApiService {

    // Obtener películas populares (soporta paginado opcional)
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): MovieResponseDto

    // Obtener el detalle de una película por su ID
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ) : MovieResponseDto
}