package ar.edu.unicen.seminarioandroid2026.ddl.domain.usecase

import ar.edu.unicen.seminarioandroid2026.ddl.data.repository.MovieRepository
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
 // Asegúrate de tener el repositorio
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    /**
     * Invoca la obtención de películas populares.
     * @param page El número de página a solicitar (por defecto 1).
     */
    suspend operator fun invoke(page: Int = 1): Result<List<Movie>>{
        // Se pasa el parámetro 'page' que requiere el repositorio
        return repository.getPopularMovies(page)
    }
}