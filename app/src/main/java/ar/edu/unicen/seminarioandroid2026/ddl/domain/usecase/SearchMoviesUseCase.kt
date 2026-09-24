package ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase

import ar.edu.unicen.seminarioandroid2026.ddl.data.repository.MovieRepository
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): Result<List<Movie>> {
        return repository.searchMovies(query, page)

    }
}