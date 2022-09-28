package com.example.bingeflix.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.bingeflix.R
import com.example.bingeflix.data.Movie
import com.example.bingeflix.data.network.TmdbService
import com.example.bingeflix.readableFormat
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment: Fragment() {

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id: Long = MovieDetailFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = MovieDetailViewModelFactory(id, activity!!.application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_detail, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            setData(it)
        })
    }

    fun setData(movie: Movie) {

        Glide.with(requireActivity()).load(TmdbService.BACKDROP_BASE_URL + movie.backdropPath)
            .error(R.drawable.error).into(movie_backdrop)


        Glide.with(requireActivity()).load(TmdbService.POSTER_BASE_URL + movie.posterPath)
            .error(R.drawable.error).into(movie_poster)

        movie_title.text = movie.title
        movie_overview.text = movie.overview
        movie_release_date.text = movie.releaseDate.readableFormat()
    }
}