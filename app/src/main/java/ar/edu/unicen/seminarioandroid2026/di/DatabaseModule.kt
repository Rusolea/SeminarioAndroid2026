package ar.edu.unicen.seminarioandroid2026.di

import android.content.Context
import androidx.room.Room
import ar.edu.unicen.seminarioandroid2026.ddl.data.local.AppDatabase
import ar.edu.unicen.seminarioandroid2026.ddl.data.local.dao.WishlistMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module //Le dice a Hilt: "Oye, aquí adentro hay recetas para fabricar objetos que la aplicación necesita".
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movies_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWishlistMovieDao(database: AppDatabase): WishlistMovieDao {
        return database.wishlistMovieDao() } }