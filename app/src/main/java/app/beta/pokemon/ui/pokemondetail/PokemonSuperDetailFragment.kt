package app.beta.pokemon.ui.pokemondetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.beta.pokemon.R
import app.beta.pokemon.databinding.PokemonSuperDetailFragmentBinding
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonSuperDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PokemonSuperDetailFragment()
    }

    private val viewModel: PokemonDetailViewModel by viewModels()
    private lateinit var viewBinding: PokemonSuperDetailFragmentBinding

    private lateinit var pokemonSkillList: PokemonDetailItemAdapter
    private lateinit var pokemonTypeList: PokemonDetailItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = PokemonSuperDetailFragmentBinding.inflate(layoutInflater)
        pokemonSkillList = PokemonDetailItemAdapter()
        pokemonTypeList = PokemonDetailItemAdapter()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Testing", "MASUK")
        loadDetail(1)
        viewBinding.pokemonSkillList.adapter = pokemonSkillList
        viewBinding.pokemonTypeList.adapter = pokemonTypeList
        viewBinding.apply {
            pokemonSprite.load(R.drawable.ic_pokemon_super)
        }
    }

    private fun loadDetail(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            val skillList: MutableList<String> = ArrayList()
            val typeList: MutableList<String> = ArrayList()
            viewModel.getPokemonDetail(id).collect { pokemonDetail ->
                pokemonDetail.data!!.pokemon_v2_pokemon.first().let { pokemon ->
                    pokemon.pokemon_v2_pokemonabilities.forEach { ability ->
                        skillList.add(ability.pokemon_v2_ability!!.name)
                    }
                    pokemon.pokemon_v2_pokemontypes.forEach { type ->
                        typeList.add(type.pokemon_v2_type!!.name)
                    }
                }
                pokemonSkillList.items = skillList
                pokemonTypeList.items = typeList
            }

        }
    }

}