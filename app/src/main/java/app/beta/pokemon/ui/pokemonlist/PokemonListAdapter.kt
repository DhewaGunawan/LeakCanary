package app.beta.pokemon.ui.pokemonlist

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.beta.pokemon.GetPokemonQuery
import app.beta.pokemon.MainActivity
import app.beta.pokemon.R
import app.beta.pokemon.databinding.PokemonListItemBinding
import coil.load

class PokemonListAdapter :
    RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    inner class PokemonListViewHolder(val viewBinding: PokemonListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<GetPokemonQuery.Pokemon_v2_pokemon>() {
        override fun areItemsTheSame(
            oldItem: GetPokemonQuery.Pokemon_v2_pokemon,
            newItem: GetPokemonQuery.Pokemon_v2_pokemon
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetPokemonQuery.Pokemon_v2_pokemon,
            newItem: GetPokemonQuery.Pokemon_v2_pokemon
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)
    var pokemons: List<GetPokemonQuery.Pokemon_v2_pokemon>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonListViewHolder {
        return PokemonListViewHolder(
            PokemonListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonListAdapter.PokemonListViewHolder, position: Int) {
        val item = pokemons[position]
        holder.viewBinding.apply {
            pokemonId.text = item.id.toString()
            pokemonName.text = item.name
            pokemonSprite.load(
                MainActivity.instance.getString(
                    R.string.sprite_url,
                    item.id
                )
            )
            if (position % 2 == 0) {
                root.background = ColorDrawable(
                    ContextCompat.getColor(
                        MainActivity.instance.baseContext,
                        R.color.pokemon_red
                    )
                )
            } else {
                root.background = ColorDrawable(
                    ContextCompat.getColor(
                        MainActivity.instance.baseContext,
                        R.color.pokemon_blue
                    )
                )
            }
        }
    }

    override fun getItemCount() = pokemons.size
}