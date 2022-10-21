package app.beta.pokemon.ui.pokemondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import app.beta.pokemon.R
import app.beta.pokemon.databinding.PokemonDetailFragmentBinding
import app.beta.pokemon.repository.db.favorite.Favorite
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PokemonDetailFragment()
    }

    private val viewModel: PokemonDetailViewModel by viewModels()
    private lateinit var viewBinding: PokemonDetailFragmentBinding
    private val args: PokemonDetailFragmentArgs by navArgs()

    private lateinit var pokemonSkillList: PokemonDetailItemAdapter
    private lateinit var pokemonTypeList: PokemonDetailItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = PokemonDetailFragmentBinding.inflate(layoutInflater)
        pokemonSkillList = PokemonDetailItemAdapter()
        pokemonTypeList = PokemonDetailItemAdapter()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDetail(args.id)
        viewBinding.pokemonSkillList.adapter = pokemonSkillList
        viewBinding.pokemonTypeList.adapter = pokemonTypeList
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.favState.collect {
                toggleFavorite(it)
            }
        }

        val pokemon = Favorite(id = args.id, name = args.name)
        viewBinding.apply {
            pokemonId.text = pokemon.id.toString()
            pokemonName.text = pokemon.name
            pokemonSprite.load(getString(R.string.sprite_url, pokemon.id))
            addFavorite.setOnClickListener {
                viewModel.addToFavorite(pokemon)
            }
            removeFavorite.setOnClickListener {
                viewModel.removeFromFavorite(pokemon)
            }
        }
    }

    private fun loadDetail(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val pokemon = Favorite(args.id, args.name)
            viewModel.isPokemonFavorited(pokemon)

            val skillList: MutableList<String> = ArrayList()
            val typeList: MutableList<String> = ArrayList()
            var habitat: String
            viewModel.getPokemonDetail(id).collect { pokemonDetail ->
                pokemonDetail.data!!.pokemon_v2_pokemon.first().let { pokemon ->
                    pokemon.pokemon_v2_pokemonabilities.forEach { ability ->
                        skillList.add(ability.pokemon_v2_ability!!.name)
                    }
                    habitat = pokemon.pokemon_v2_pokemonspecy!!.pokemon_v2_pokemonhabitat!!.name
                    pokemon.pokemon_v2_pokemontypes.forEach { type ->
                        typeList.add(type.pokemon_v2_type!!.name)
                    }
                }
                pokemonSkillList.items = skillList
                pokemonTypeList.items = typeList
                viewBinding.pokemonHabitat.text = habitat
                viewBinding.pokemonHabitat.visibility = View.VISIBLE
            }

        }
    }

    private fun toggleFavorite(fav: Boolean) {
        if (fav) {
            requireActivity().runOnUiThread {
                viewBinding.addFavorite.visibility = View.GONE
                viewBinding.removeFavorite.visibility = View.VISIBLE
            }
        } else {
            requireActivity().runOnUiThread {
                viewBinding.addFavorite.visibility = View.VISIBLE
                viewBinding.removeFavorite.visibility = View.GONE
            }
        }
    }

}
