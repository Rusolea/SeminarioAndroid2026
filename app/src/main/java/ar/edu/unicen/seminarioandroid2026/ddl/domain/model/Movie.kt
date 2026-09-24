package ar.edu.unicen.seminarioandroid2026.ddl.domain.model

data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?, // URL completa lista para Coil
    val backdropUrl: String?,
    val releaseDate: String,
    val voteAverage: Double


)