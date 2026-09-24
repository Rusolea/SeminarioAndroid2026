package ar.edu.unicen.seminarioandroid2026.ddl.data.mapper

import ar.edu.unicen.seminarioandroid2026.ddl.data.remote.dto.MovieDto
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie

//Responsabilidad única: La carpeta mapper tiene la única misión de transformar datos entre capas.
//Capa de Datos (Data Layer): Los mappers pertenecen a la capa de datos porque son los que "conocen" tanto el formato de la API (MovieDto) como el formato del Dominio (Movie).
//Dominio Limpio: Tu clase Movie.kt en la capa de dominio se mantiene "pura", sin saber nada de URLs de imágenes de TMDB ni de cómo se llaman los campos en el JSON.

/**
 * Convierte un objeto DTO (Data Transfer Object) de la API
 * en un objeto de Dominio que usará la UI.
 */
fun MovieDto.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        // Construimos la URL completa para que la UI no tenga que saber de rutas de TMDB
        posterUrl = this.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = this.backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = this.releaseDate ?: "Fecha desconocida",
        voteAverage = this.voteAverage
    )
}