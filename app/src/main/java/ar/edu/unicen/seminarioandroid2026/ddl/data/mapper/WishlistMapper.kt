package ar.edu.unicen.seminarioandroid2026.ddl.data.mapper

import ar.edu.unicen.seminarioandroid2026.ddl.data.local.WishlistMovieEntity
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.Movie
import ar.edu.unicen.seminarioandroid2026.ddl.domain.model.MovieDetail

fun WishlistMovieEntity.toDomain() : Movie{
    return Movie(
        id = this.id,
        title = this.title,
        overview = "",
        posterUrl = this.posterUrl,
        releaseDate = "",
        voteAverage = this.voteAverage,
        backdropUrl = this.backdropUrl,

    )

}

fun MovieDetail.toWishlistEntity() : WishlistMovieEntity{
    return WishlistMovieEntity(
        id = this.id,
        title = this.title,
        posterUrl = this.posterUrl ?: this.backdropUrl,
        backdropUrl = this.backdropUrl,
        voteAverage = this.voteAverage ?: 0.0,
    )
}