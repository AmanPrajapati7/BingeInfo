package com.example.bingeflix.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bingeflix.data.network.TmdbService

class MovieListRepository(context: Application) {
    private val movieListDao: MovieListDao = MovieDatabase.getDatabase(context).movieListDao()
    private val tmdbService by lazy { TmdbService.getInstance() }


    fun getMovies(): LiveData<List<Movie>> {
        return movieListDao.getMovies()
    }

    suspend fun fetchFromNetwork() {
        val result = tmdbService.getMovies()
        if(result.isSuccessful) {
            val movieList = result.body()
            movieList?.let{ movieListDao.insertMovies(it.results) }
        }
    }

    suspend fun deleteAllData() {
        movieListDao.deleteAllData()
    }
}