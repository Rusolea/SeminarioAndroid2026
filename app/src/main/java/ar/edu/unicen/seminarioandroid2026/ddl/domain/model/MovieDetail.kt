package ar.edu.unicen.seminarioandroid2026.ddl.domain.model

import ar.edu.unicen.seminarioandroid2026.ddl.data.remote.dto.GenreDto
import com.google.gson.annotations.SerializedName

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
    val backdropUrl: String?

)