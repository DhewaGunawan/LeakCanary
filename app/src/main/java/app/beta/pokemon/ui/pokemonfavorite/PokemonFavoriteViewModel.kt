package app.beta.pokemon.ui.pokemonfavorite

import androidx.lifecycle.ViewModel
import app.beta.pokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonFavoriteViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    fun getFavorites() = pokemonRepository.getPokemonFavorite()

}