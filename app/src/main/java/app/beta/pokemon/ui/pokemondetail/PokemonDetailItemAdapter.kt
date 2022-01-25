package app.beta.pokemon.ui.pokemondetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.beta.pokemon.databinding.PokemonDetailItemBinding

class PokemonDetailItemAdapter :
    RecyclerView.Adapter<PokemonDetailItemAdapter.PokemonDetailViewHolder>() {

    inner class PokemonDetailViewHolder(val viewBinding: PokemonDetailItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    private val differItem = AsyncListDiffer(this, diffUtil)
    var items: List<String>
        get() = differItem.currentList
        set(value) {
            differItem.submitList(value)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonDetailItemAdapter.PokemonDetailViewHolder {
        return PokemonDetailViewHolder(
            PokemonDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PokemonDetailItemAdapter.PokemonDetailViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.viewBinding.itemName.text = item
    }

    override fun getItemCount() = items.size
}