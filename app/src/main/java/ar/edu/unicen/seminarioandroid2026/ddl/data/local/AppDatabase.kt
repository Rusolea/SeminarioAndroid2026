package ar.edu.unicen.seminarioandroid2026.ddl.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unicen.seminarioandroid2026.ddl.data.local.dao.WishlistMovieDao

@Database(entities = [WishlistMovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistMovieDao(): WishlistMovieDao
}