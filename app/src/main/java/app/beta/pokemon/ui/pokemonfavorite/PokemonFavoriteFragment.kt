package app.beta.pokemon.ui.pokemonfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.beta.pokemon.databinding.PokemonFavoriteFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonFavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = PokemonFavoriteFragment()
    }

    private val viewModel: PokemonFavoriteViewModel by viewModels()
    private lateinit var viewBinding: PokemonFavoriteFragmentBinding
    private lateinit var favListAdapter: PokemonFavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = PokemonFavoriteFragmentBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavorites()
        favListAdapter = PokemonFavoriteAdapter()
        viewBinding.favoriteList.adapter = favListAdapter

    }

    private fun getFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavorites().collect {
                favListAdapter.pokemonsFav = it
            }
        }
    }
}