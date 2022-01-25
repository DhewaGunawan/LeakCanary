package app.beta.pokemon.ui.pokemonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
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

    private val diffUtilPokemon =
        object : DiffUtil.ItemCallback<GetPokemonQuery.Pokemon_v2_pokemon>() {
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

//    private val diffUtilFavorite = object : DiffUtil.ItemCallback<Favorite>() {
//        override fun areItemsTheSame(
//            oldItem: Favorite,
//            newItem: Favorite
//        ): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(
//            oldItem: Favorite,
//            newItem: Favorite
//        ): Boolean {
//            return oldItem == newItem
//        }
//    }

    private val differPokemon = AsyncListDiffer(this, diffUtilPokemon)
    var pokemons: List<GetPokemonQuery.Pokemon_v2_pokemon>
        get() = differPokemon.currentList
        set(value) {
            differPokemon.submitList(value)
        }
//    private val differFavorite = AsyncListDiffer(this, diffUtilFavorite)
//    var pokemonsFav: List<Favorite>
//        get() = differFavorite.currentList
//        set(value) {
//            differFavorite.submitList(value)
//        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonListViewHolder {
        val viewHolder = PokemonListViewHolder(
            PokemonListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.viewBinding.root.setOnClickListener {
            Navigation.findNavController(viewHolder.viewBinding.root)
                .navigate(
                    PokemonListFragmentDirections.openDetail(
                        id = pokemons[viewHolder.layoutPosition].id,
                        name = pokemons[viewHolder.layoutPosition].name
                    )
                )
        }
        return viewHolder
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
//            val pokemonItem = Favorite(id = item.id, name = item.name)
//            if (pokemonsFav.contains(pokemonItem)) {
//                root.background = ColorDrawable(
//                    ContextCompat.getColor(
//                        MainActivity.instance.baseContext,
//                        R.color.pokemon_red
//                    )
//                )
//            }
        }
    }

    override fun getItemCount() = pokemons.size
}