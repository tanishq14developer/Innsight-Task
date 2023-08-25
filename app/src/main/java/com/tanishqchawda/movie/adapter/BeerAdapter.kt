package com.tanishqchawda.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tanishqchawda.movie.R
import com.tanishqchawda.movie.databinding.BottomDetailsBeerBinding
import com.tanishqchawda.movie.databinding.SingleItemBinding


import com.tanishqchawda.movie.model.Search

class BeerAdapter(private val context: Context, val onShareClick:(Search) ->Unit):PagingDataAdapter<Search,BeerAdapter.MyViewHolder>(BeerDiffUtils()) {
    inner class MyViewHolder(val binding: SingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(beerResponseModelItem: Search){
           binding.beerModel = beerResponseModelItem

        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeerAdapter.MyViewHolder {
        val binding =
            SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerAdapter.MyViewHolder, position: Int) {
        val beer =getItem(position)
        holder.bind(beer!!)
        holder.binding.apply {
            Glide.with(context).load(beer.poster).override(100, 100).into(iBeerImage)
            holder.itemView.setOnClickListener {
                details(getItem(position)!!,position)
            }

        }

    }


    private fun details(beerResponseModelItem: Search, position:Int) {
        val binding: BottomDetailsBeerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.bottom_details_beer,
            null,
            false
        )
        val dialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
        binding.apply {
            beerModelData = beerResponseModelItem
            positionBeer = position
            Glide.with(context).load(beerResponseModelItem.poster).override(100, 100)
                .into(poster)
            close.setOnClickListener {
                dialog.dismiss()
            }

        }

        dialog.setContentView(binding.root)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()
    }
}
class BeerDiffUtils : DiffUtil.ItemCallback<Search>() {
    override fun areItemsTheSame(
        oldItem: Search,
        newItem: Search
    ): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }

    override fun areContentsTheSame(
        oldItem: Search,
        newItem: Search
    ): Boolean {
        return oldItem == newItem
    }
}
