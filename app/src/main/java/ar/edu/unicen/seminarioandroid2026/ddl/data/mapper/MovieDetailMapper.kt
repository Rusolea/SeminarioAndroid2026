package ar.edu.unicen.seminarioandroid2026.ddl.data.mapper

import ar.edu.unicen.seminarioandroid2026.ddl.data.remote.dto.MovieDetailDto
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

        fun MovieDetailDto.toDomain() : MovieDetail {
            val formattedRuntime = runtime?.let { "${it / 60}h ${it % 60}m" } ?: "N/A"

            return MovieDetail(
                id = this.id,
                title = this.title,
                overview = this.overview ?: "Sin sinopsis disponible",
                posterPath = this.posterPath?.let { "$IMAGE_BASE_URL$it" },
                backdropPath = this.backdropPath?.let { "$IMAGE_BASE_URL$it" },
                releaseDate = this.releaseDate ?: "N/A",
                voteAverage = this.voteAverage,
                runtimeFormatted = formattedRuntime,
                genres = this.genres?.map { it.name } ?: emptyList(),
                posterUrl = this.posterPath?.let { "$IMAGE_BASE_URL$it" },
                backdropUrl = this.backdropPath?.let { "$IMAGE_BASE_URL$it" }




            )
        }








