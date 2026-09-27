package ar.edu.unicen.seminarioandroid2026.ddl.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "wishlist_movies")
data class WishlistMovieEntity (
    @PrimaryKey val id : Int,
    val title : String,
    val posterUrl : String?,
    val voteAverage : Double,
    val backdropUrl: String?

)