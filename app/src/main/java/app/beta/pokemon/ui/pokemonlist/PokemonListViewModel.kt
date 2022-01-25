package app.beta.pokemon.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.beta.pokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private var _page = MutableStateFlow(0)
    val page = _page.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    fun getPokemons() = pokemonRepository.getPokemonList(page.value)

    fun nextPage() = viewModelScope.launch {
        val next = page.value + 1
        _page.emit(next)
    }

    fun prevPage() = viewModelScope.launch {
        if (page.value > 0) {
            val next = page.value - 1
            _page.emit(next)
        }
    }

    fun toggleLoading() {
        viewModelScope.launch {
            _loadingState.emit(!loadingState.value)
        }
    }

    fun getPokemonFavCount() = pokemonRepository.getPokemonListSize()
}
