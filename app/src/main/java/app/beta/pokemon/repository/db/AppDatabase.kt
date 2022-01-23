package app.beta.pokemon.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.beta.pokemon.repository.db.favorite.Favorite
import app.beta.pokemon.repository.db.favorite.FavoriteDao


@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}