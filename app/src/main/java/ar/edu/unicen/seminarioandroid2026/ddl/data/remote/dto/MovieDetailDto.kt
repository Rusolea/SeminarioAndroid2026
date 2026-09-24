package ar.edu.unicen.seminarioandroid2026.ddl.data.remote.dto

import com.google.gson.annotations.SerializedName

//Al seleccionar una película de la lista, se deberá navegar a una segunda pantalla donde se
//podrá visualizar más información sobre la misma (movie-details4). Esta pantalla deberá
//mostrar (como mínimo) los siguientes datos:
// el título de la película,
// la portada
//(image-basics5),
// la sinopsis / resumen,
// los géneros y el
// rating de la película. Pueden mostrar
//más información si así lo desean.

class MovieDetailDto (
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("genres")
    val genres: List<GenreDto>
)

data class GenreDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)