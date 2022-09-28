package com.example.bingeflix.data.network

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/original"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/original"

        private val retrofitService by lazy {
            val interceptor = Interceptor { chain ->  
                val request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("api_key", "7ddb8b3588a087cd25dd6da5f48953f3")
                    .build()

                val newRequest = request.newBuilder().url(url).build()
                chain.proceed(newRequest)
            }

            val httpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(TmdbService::class.java)
        }

        fun getInstance(): TmdbService {
            return retrofitService
        }
    }

//    @GET("discover/movie?certification_country=US&adult=false&vote_count.gte=100&with_original_language=en&sort_by=primary_release_date.desc")
//    suspend fun getMovies(country: String): Response<TmdbMovieList>

    @GET("discover/movie?")
    suspend fun getMovies(@Query("with_origin_country") name: String = "CH"): Response<TmdbMovieList>

}