package com.mehedisoftdev.moviesapi.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mehedisoftdev.moviesapi.views.MovieDetailsActivity
import com.mehedisoftdev.moviesapi.R
import com.mehedisoftdev.moviesapi.models.Search

class MovieListItemAdapter(private val context: Context): PagingDataAdapter<Search, MovieListItemAdapter.MovieViewHolder>(DiffUtilCallBacks()) {

    inner class MovieViewHolder(itemView: View): ViewHolder(itemView) {
        // creating reference
        val ivPoster = itemView.findViewById<ImageView>(R.id.iv_poster)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val tvType = itemView.findViewById<TextView>(R.id.tv_type)
        val tvReleaseYear = itemView.findViewById<TextView>(R.id.tv_release)

        fun bind(movie: Search?) {
            // setup image
            Glide.with(context).load(movie?.Poster).into(ivPoster)
            // rest of the text view
            tvTitle.text = movie?.Title
            tvType.text = String.format(context.getString(R.string.movie_type), movie?.Type)
            tvReleaseYear.text = String.format(context.getString(R.string.release_year), movie?.Year)
        }
    }

    class DiffUtilCallBacks(): DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem.Year == newItem.Year
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Search? = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            val intent: Intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("MOVIEID", movie?.imdbID)
            context.startActivity(intent)
        }
    }
}