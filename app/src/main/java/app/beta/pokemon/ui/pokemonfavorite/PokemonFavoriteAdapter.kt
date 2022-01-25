package app.beta.pokemon.ui.pokemonfavorite

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.beta.pokemon.MainActivity
import app.beta.pokemon.R
import app.beta.pokemon.databinding.PokemonListItemBinding
import app.beta.pokemon.repository.db.favorite.Favorite
import coil.load

class PokemonFavoriteAdapter :
    RecyclerView.Adapter<PokemonFavoriteAdapter.PokemonFavoriteViewHolder>() {

    inner class PokemonFavoriteViewHolder(val viewBinding: PokemonListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private val diffUtilFavorite = object : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(
            oldItem: Favorite,
            newItem: Favorite
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Favorite,
            newItem: Favorite
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differFavorite = AsyncListDiffer(this, diffUtilFavorite)
    var pokemonsFav: List<Favorite>
        get() = differFavorite.currentList
        set(value) {
            differFavorite.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonFavoriteViewHolder {
        val viewHolder = PokemonFavoriteViewHolder(
            PokemonListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.viewBinding.root.setOnClickListener {
            Navigation.findNavController(viewHolder.viewBinding.root)
                .navigate(
                    PokemonFavoriteFragmentDirections.openDetail(
                        id = pokemonsFav[viewHolder.layoutPosition].id,
                        name = pokemonsFav[viewHolder.layoutPosition].name
                    )
                )
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PokemonFavoriteViewHolder, position: Int) {
        val item = pokemonsFav[position]
        holder.viewBinding.apply {
            pokemonId.text = item.id.toString()
            pokemonName.text = item.name
            pokemonSprite.load(
                MainActivity.instance.getString(
                    R.string.sprite_url,
                    item.id
                )
            )
            root.background = ColorDrawable(
                ContextCompat.getColor(
                    MainActivity.instance.baseContext,
                    R.color.pokemon_red
                )
            )
        }
    }

    override fun getItemCount() = pokemonsFav.size

}