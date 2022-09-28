package com.example.bingeflix.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.bingeflix.R
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment: Fragment() {

    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(movie_list) {

            adapter = MovieAdapter {
                findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it))
            }
        }

        viewModel.movies.observe(viewLifecycleOwner, {
            (movie_list.adapter as MovieAdapter).submitList(it)
            if(it.isEmpty()) {
                viewModel.fetchFromNetwork()
            }
            swipe_refresh.isRefreshing = false
        })

        swipe_refresh.setOnRefreshListener {
            viewModel.refreshData()
        }
    }
}