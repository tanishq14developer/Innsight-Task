package com.tanishqchawda.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tanishqchawda.movie.databinding.ItemLoaderBinding


class LoaderAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoaderBinding.inflate(inflater, parent, false)
        return LoaderViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}

class LoaderViewHolder(
    private val binding: ItemLoaderBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
    }

    fun bind(loadState: LoadState) {
        binding.loadState = loadState
        binding.executePendingBindings()
    }
}

