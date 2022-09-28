package com.example.bingeflix.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bingeflix.R
import com.example.bingeflix.data.Movie
import com.example.bingeflix.data.network.TmdbService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.fragment_movie_detail.movie_title
import kotlinx.android.synthetic.main.list_item.*

class MovieAdapter(private val listener: (Long) -> Unit) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

            init {
                itemView.setOnClickListener {
                    listener.invoke(getItem(adapterPosition).id)
                }
            }

        fun bind(movie: Movie) {
            Glide.with(containerView)
                .load(TmdbService.BACKDROP_BASE_URL + movie.posterPath)
                .error(R.drawable.error).into(item_poster)

            item_title.text = movie.title
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return  oldItem == newItem
        }
    }

}