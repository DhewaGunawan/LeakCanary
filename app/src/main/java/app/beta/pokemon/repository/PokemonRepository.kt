package app.beta.pokemon.repository

import app.beta.pokemon.GetPokemonQuery
import app.beta.pokemon.repository.api.PokemonApiDataSource
import app.beta.pokemon.repository.db.favorite.Favorite
import app.beta.pokemon.repository.db.favorite.FavoriteDataSource
import com.apollographql.apollo3.api.ApolloResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PokemonRepository @Inject constructor(
    private val pokemonApiDataSource: PokemonApiDataSource,
    val pokemonFavDataSource: FavoriteDataSource
) {

//    fun getPokemonList(offset: Int = 1) = flow {
//        val pokemon: MutableList<Pokemon> = ArrayList<Pokemon>().toMutableList()
//        pokemonApiDataSource.getPokemon(offset).map {
//            Log.d("run_scope", "mapping")
//            val pokemonData = it.data!!.pokemon_v2_pokemon
//            pokemonData.forEach { item ->
//                val pokemonItem = Pokemon(id = item.id, name = item.name)
//                pokemon += pokemonItem
//                Log.d("emit_test", pokemon[1].name)
//            }
//            emit(pokemon)
//        }
//    }

    fun getPokemonList(): Flow<ApolloResponse<GetPokemonQuery.Data>> {
        return pokemonApiDataSource.getPokemon()
    }

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