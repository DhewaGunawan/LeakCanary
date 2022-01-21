package app.beta.pokemon.appdatabase.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_list")
data class Favorite(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)