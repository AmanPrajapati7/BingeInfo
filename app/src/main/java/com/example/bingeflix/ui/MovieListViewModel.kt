package com.example.bingeflix.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bingeflix.data.Movie
import com.example.bingeflix.data.MovieListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(application: Application): AndroidViewModel(application) {

    private val repo: MovieListRepository = MovieListRepository(application)

    val movies: LiveData<List<Movie>> = repo.getMovies()

    fun fetchFromNetwork() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.fetchFromNetwork()
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllData()
        }
        fetchFromNetwork()
    }


}