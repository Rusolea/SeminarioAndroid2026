package ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase

import ar.edu.unicen.seminarioandroid2026.ddl.data.repository.MovieRepository
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import javax.inject.Inject

class GetRecommendedMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<List<Movie>> {
        return repository.getRecommendations(movieId)
    }

}