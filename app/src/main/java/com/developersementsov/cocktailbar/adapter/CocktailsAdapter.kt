package com.developersementsov.cocktailbar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developersementsov.cocktailbar.databinding.DrinkItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class CocktailsAdapter(
    private val context: Context,
    private val drinkItems: MutableList<DrinkItem>
) : RecyclerView.Adapter<CocktailsAdapter.CocktailsViewHolder>() {
    var onItemClick: ((DrinkItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        val binding = DrinkItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CocktailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
        val drinkItem = drinkItems[position]
        holder.bind(drinkItem)
    }

    override fun getItemCount(): Int {
        return drinkItems.size
    }

    inner class CocktailsViewHolder(drinkItemBinding: DrinkItemBinding) :
        RecyclerView.ViewHolder(drinkItemBinding.root) {

        private val binding = drinkItemBinding

        fun bind(drinkItem: DrinkItem) {
            setImage(drinkItem.imageUrl)
            binding.drinkName.text = drinkItem.drinkName
        }

        private fun setImage(url: String) {
            Picasso.get().load("$url/preview").into(binding.imagePreView, object : Callback {
                override fun onSuccess() {
                    binding.itemProgressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    // do nothing
                }
            }
            )
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(drinkItems[adapterPosition])
            }
        }
    }
}