package app.beta.pokemon.appdatabase

import androidx.room.Database
import app.beta.pokemon.appdatabase.favorite.Favorite
import app.beta.pokemon.appdatabase.favorite.FavoriteDao


@Database(entities = [Favorite::class], version = 1)
abstract class AppDatabase {
    abstract fun favoriteDao(): FavoriteDao
}