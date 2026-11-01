package ar.edu.unicen.seminarioandroid2026.ddl.domain.model



data class MovieDetail (
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val runtimeFormatted: String,
    val genres: List<String>,
    val posterUrl: String?,
    val backdropUrl: String?,
    val homepage: String?

)