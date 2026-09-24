package ar.edu.unicen.seminarioandroid2026.ddl.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieDto>, // Aquí es donde se usa tu MovieDto

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)