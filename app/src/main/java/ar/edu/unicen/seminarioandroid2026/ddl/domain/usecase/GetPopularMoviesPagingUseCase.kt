package ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase

import androidx.paging.PagingData
import ar.edu.unicen.seminarioandroid2026.ddl.data.repository.MovieRepository
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesPagingUseCase @Inject constructor(
    private val repository: MovieRepository // 🛠️ Depende del repositorio, no de sí mismo
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getPopularMoviesStream()
    }
}