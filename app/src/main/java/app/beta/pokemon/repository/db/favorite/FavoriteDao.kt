package app.beta.pokemon.repository.db.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_list")
    fun getAllFavorite(): Flow<List<Favorite>>

    @Query("SELECT COUNT(*) FROM favorite_list")
    fun getFavoriteSize(): Flow<Int>

    @Insert
    fun insertFavorite(favorite: Favorite)

    @Delete
    fun deleteFavorite(favorite: Favorite)

    @Query("SELECT EXISTS( SELECT 1 FROM favorite_list WHERE id = :id )")
    fun isFavorite(id: Int): Flow<Boolean>
}