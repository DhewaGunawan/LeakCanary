package app.beta.pokemon.ui.pokemonlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenStarted
import androidx.navigation.Navigation
import app.beta.pokemon.R
import app.beta.pokemon.databinding.PokemonListFragmentBinding
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    companion object {
        fun newInstance() = PokemonListFragment()
    }

    private val viewModel: PokemonListViewModel by viewModels()

//    private lateinit var viewBinding: PokemonListFragmentBinding
    private var _binding: PokemonListFragmentBinding? = null
    private val viewBinding get() = _binding!!

//    private lateinit var pokemonListAdapter: PokemonListAdapter
    private var _pokemonListAdapter: PokemonListAdapter? = null
    private val pokemonListAdapter get() = _pokemonListAdapter!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PokemonListFragmentBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populatePokemon()
        _pokemonListAdapter = PokemonListAdapter()
        viewBinding.apply {
            cardViewTop.apply {
                pokemonSprite.load(R.drawable.ic_pokemon_super)
                pokemonId.text = context.getString(R.string.id_pokemon_super)
                pokemonName.text = context.getString(R.string.name_super)
                setOnClickListener {
                    Log.d("Testing", "onViewCreated: masuk openSuperDetail")
                    Navigation.findNavController(viewBinding.root)
                        .navigate(PokemonListFragmentDirections.openSuperDetail())
                }
            }
        }
        viewBinding.pokemonList.adapter = pokemonListAdapter
        viewBinding.favoriteCollection.setOnClickListener {
            Navigation.findNavController(viewBinding.root)
                .navigate(PokemonListFragmentDirections.openFavorites())
        }
        viewBinding.doNext.setOnClickListener {
            loadNextPage()
        }
        viewBinding.doPrev.setOnClickListener {
            loadPrevPage()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loadingState.collect {
                        if (it) {
                            viewBinding.apply {
                                pokemonList.visibility = View.GONE
                                loading.visibility = View.VISIBLE
                                doNext.isEnabled = false
                                doPrev.isEnabled = false
                            }
                        } else {
                            viewBinding.apply {
                                loading.visibility = View.GONE
                                pokemonList.visibility = View.VISIBLE
                                doNext.isEnabled = true
                                doPrev.isEnabled = true
                            }
                        }
                    }
                }
                launch {
                    viewModel.page.collect { pageNum ->
                        run {
                            if (pageNum == 0) {
                                viewBinding.doPrev.visibility = View.INVISIBLE
                            } else {
                                viewBinding.doPrev.visibility = View.VISIBLE
                            }
                            launch {
                                viewModel.getPokemons().collect {
                                    viewModel.toggleLoading()
                                    pokemonListAdapter.pokemons = it.data!!.pokemon_v2_pokemon
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadNextPage() {
        viewBinding.pokemonList.recycledViewPool.clear()
        viewModel.toggleLoading()
        viewModel.nextPage()
    }

    private fun loadPrevPage() {
        viewBinding.pokemonList.recycledViewPool.clear()
        viewModel.toggleLoading()
        viewModel.prevPage()
    }

    private fun populatePokemon() {
        viewLifecycleOwner.lifecycleScope.launch {
            whenStarted {
                launch {
                    viewModel.toggleLoading()
                    viewModel.getPokemons().collectLatest {
                        pokemonListAdapter.pokemons = it.data!!.pokemon_v2_pokemon
                    }
                }
                launch {
                    viewModel.getPokemonFavCount().collect {
                        if (it == 0) viewBinding.favoriteCollection.visibility = View.GONE
                        else {
                            viewBinding.favoriteCollection.visibility = View.VISIBLE
                            viewBinding.favoritesCount.text = it.toString()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _pokemonListAdapter = null
    }

}