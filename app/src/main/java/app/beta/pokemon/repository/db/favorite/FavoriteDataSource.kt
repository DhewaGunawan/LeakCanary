package app.beta.pokemon.repository.db.favorite

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FavoriteDataSource @Inject constructor(private val favDao: FavoriteDao) {

    val getFavorite = favDao.getAllFavorite()

    val getFavoriteSize = favDao.getFavoriteSize()

    fun insertFavorite(favorite: Favorite) {
        favDao.insertFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        favDao.deleteFavorite(favorite)
    }

    fun isFavorite(favorite: Favorite) = favDao.isFavorite(favorite.id)
}