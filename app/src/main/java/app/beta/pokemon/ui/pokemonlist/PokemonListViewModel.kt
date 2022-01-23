package app.beta.pokemon.ui.pokemonlist

import androidx.lifecycle.ViewModel
import app.beta.pokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

//    fun getPokemons(offset: Int): Flow<ApolloResponse<GetPokemonQuery.Data>> {
//        Log.d("run_scope", "running_view_model")
//        return pokemonRepository.getPokemonList(offset)
//    }

    fun getPokemons() = pokemonRepository.getPokemonList()

    fun getPokemonFavCount() = pokemonRepository.getPokemonListSize()
}
