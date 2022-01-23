package app.beta.pokemon.ui.pokemonlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.beta.pokemon.databinding.PokemonListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    companion object {
        fun newInstance() = PokemonListFragment()
    }

    private val viewModel: PokemonListViewModel by viewModels()

    lateinit var viewBinding: PokemonListFragmentBinding
    lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = PokemonListFragmentBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populatePokemon()
        pokemonListAdapter = PokemonListAdapter()
        viewBinding.pokemonList.adapter = pokemonListAdapter
    }

    private fun populatePokemon() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPokemons().collect {
                pokemonListAdapter.pokemons = it.data!!.pokemon_v2_pokemon
            }
            viewModel.getPokemonFavCount().collect {
                Log.d("size", it.toString())
                if (it == 0) viewBinding.favoriteCollection.visibility = View.GONE
                else viewBinding.favoriteCollection.visibility = View.VISIBLE
            }
        }
    }

}