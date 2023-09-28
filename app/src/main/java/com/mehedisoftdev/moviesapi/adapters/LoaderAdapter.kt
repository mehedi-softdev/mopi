package com.mehedisoftdev.moviesapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mehedisoftdev.moviesapi.R

class LoaderAdapter: LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {
    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.loader, parent, false)
        return LoaderViewHolder(view)
    }
    class LoaderViewHolder(itemView: View): ViewHolder(itemView) {
        val loader = itemView.findViewById<ProgressBar>(R.id.loader_progress_bar)
        fun bind(loadState: LoadState) {
            loader.isVisible = loadState is LoadState.Loading
        }
    }

}