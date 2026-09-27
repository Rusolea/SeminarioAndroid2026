package ar.edu.unicen.seminarioandroid2026.ddl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.edu.unicen.seminarioandroid2026.ddl.data.local.WishlistMovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WishlistMovieDao {

    @Query("SELECT * FROM wishlist_movies")
    fun getWishlistMovies(): Flow<List<WishlistMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_movies WHERE id = :movieId)")
    fun isMovieInWishlist(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: WishlistMovieEntity)

    @Query("DELETE FROM wishlist_movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

}

