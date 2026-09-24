package ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase

import ar.edu.unicen.seminarioandroid2026.ddl.data.repository.MovieRepository
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository : MovieRepository) {

    suspend operator fun invoke(movieId: Int ) : Result<MovieDetail>{
        return repository.getMovieDetails(movieId)

    }


}