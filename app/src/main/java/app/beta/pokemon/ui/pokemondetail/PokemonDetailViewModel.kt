package app.beta.pokemon.ui.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.beta.pokemon.repository.PokemonRepository
import app.beta.pokemon.repository.db.favorite.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private var _favState = MutableStateFlow(value = false)
    val favState = _favState.asStateFlow()

    fun getPokemonDetail(id: Int) = pokemonRepository.getPokemonDetail(id)

    fun isPokemonFavorited(pokemon: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.isPokemonFavorite(pokemon).collect {
                _favState.emit(it)
            }
        }
    }

    fun addToFavorite(pokemon: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.insertPokemonFavorite(pokemon)
            _favState.emit(true)
        }
    }

    fun removeFromFavorite(pokemon: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.deletePokemonFavorite(pokemon)
            _favState.emit(false)
        }
    }

}