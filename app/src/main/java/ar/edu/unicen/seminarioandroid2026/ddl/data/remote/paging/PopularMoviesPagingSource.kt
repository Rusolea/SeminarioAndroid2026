package ar.edu.unicen.seminarioandroid2026.ddl.data.remote.paging

import android.annotation.SuppressLint
import android.net.http.HttpException
import androidx.compose.ui.unit.minus
import androidx.compose.ui.unit.plus
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.edu.unicen.seminarioandroid2026.ddl.data.mapper.toDomain
import ar.edu.unicen.seminarioandroid2026.ddl.data.remote.TmdbApiService
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import java.io.IOException

class PopularMoviesPagingSource(
    private val apiService: TmdbApiService
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Determina la clave para refrescar la lista después de una invalidación
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    @SuppressLint("NewApi")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1 // La página inicial es 1

        return try {
            val response = apiService.getPopularMovies(page = position)

            // Mapeamos los resultados de la API (DTO) a nuestro modelo de dominio (Movie)
            val movies = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = movies,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            // Error de red (sin internet)
            LoadResult.Error(exception)
        } catch (@SuppressLint("NewApi") exception: HttpException) {
            // Error del servidor (404, 500, etc.)
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            // Cualquier otro error inesperado
            LoadResult.Error(exception)
        }
    }
}