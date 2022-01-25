package app.beta.pokemon.repository

import app.beta.pokemon.repository.api.PokemonApiDataSource
import app.beta.pokemon.repository.db.favorite.Favorite
import app.beta.pokemon.repository.db.favorite.FavoriteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PokemonRepository @Inject constructor(
    private val pokemonApiDataSource: PokemonApiDataSource,
    private val pokemonFavDataSource: FavoriteDataSource
) {

    fun getPokemonList(page: Int) = pokemonApiDataSource.getPokemon(page)

    fun getPokemonDetail(id: Int) = pokemonApiDataSource.getPokemonDetail(id)

    fun getPokemonFavorite() = pokemonFavDataSource.getFavorite

    fun getPokemonListSize() = pokemonFavDataSource.getFavoriteSize

    fun insertPokemonFavorite(pokemon: Favorite) {
        pokemonFavDataSource.insertFavorite(pokemon)
    }

    fun deletePokemonFavorite(pokemon: Favorite) {
        pokemonFavDataSource.deleteFavorite(pokemon)
    }

    fun isPokemonFavorite(pokemon: Favorite): Flow<Boolean> {
        return pokemonFavDataSource.isFavorite(pokemon)
    }

}