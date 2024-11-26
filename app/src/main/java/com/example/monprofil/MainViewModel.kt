package com.example.monprofil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    val movies = MutableStateFlow<List<LastMovieData>>(emptyList())
    val tvShows = MutableStateFlow<List<LastTvData>>(emptyList())
    val actors = MutableStateFlow<List<LastActeurData>>(emptyList())
    val cast = MutableStateFlow<List<LastActeurData>>(emptyList())

    private val apiKey = "d56137a7d2c77892dd70729b2a4ee56b"

    private val tmdbApi: Api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(Api::class.java)

    // Récupérer les films tendance de la semaine
    fun fetchLatestMovies() {
        viewModelScope.launch {
            try {
                val result = tmdbApi.lastmovies(apiKey)
                movies.value = result.results
                Log.d("MoviesScreen", "Fetched movies: ${result.results.size}")
            } catch (e: Exception) {
                Log.e("MoviesScreen", "Error fetching movies", e)
            }
        }
    }

    // Récupérer les séries tendance de la semaine
    fun fetchLatestTvShows() {
        viewModelScope.launch {
            try {
                val result = tmdbApi.lasttv(apiKey)
                tvShows.value = result.results
                Log.d("TvScreen", "Fetched TV shows: ${result.results.size}")
            } catch (e: Exception) {
                Log.e("TvScreen", "Error fetching TV shows", e)
            }
        }
    }

    // Récupérer les acteurs populaires de la semaine
    fun fetchPopularActors() {
        viewModelScope.launch {
            try {
                val result = tmdbApi.lastacteur(apiKey)
                actors.value = result.results
                Log.d("ActorsScreen", "Fetched actors: ${result.results.size}")
            } catch (e: Exception) {
                Log.e("ActorsScreen", "Error fetching actors", e)
            }
        }
    }

    // Récupérer les acteurs d'un film
    fun fetchActorsOfMovie(movieId: Int) {
        viewModelScope.launch {
            try {
                val result = tmdbApi.getActorsOfMovie(movieId, apiKey)
                cast.value = result.cast
                Log.d("ActorsOfMovie", "Fetched ${result.cast.size} actors for movie $movieId")
            } catch (e: Exception) {
                Log.e("ActorsOfMovie", "Error fetching actors for movie $movieId", e)
            }
        }
    }

    // Récupérer les acteurs d'une série
    fun fetchActorsOfTv(tvShowId: Int) {
        viewModelScope.launch {
            try {
                val result = tmdbApi.getActorsOfTv(tvShowId, apiKey)
                cast.value = result.cast
                Log.d("ActorsOfTv", "Fetched ${result.cast.size} actors for TV show $tvShowId")
            } catch (e: Exception) {
                Log.e("ActorsOfTv", "Error fetching actors for TV show $tvShowId", e)
            }
        }
    }

    // Récupérer un film par son ID
    fun getMovieById(movieId: String): LastMovieData? {
        return movies.value.firstOrNull { it.id.toString() == movieId }
    }

    // Récupérer une série par son ID
    fun getTvShowById(tvShowId: String): LastTvData? {
        return tvShows.value.firstOrNull { it.id.toString() == tvShowId }
    }

    // Rechercher des films par mot-clé
    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val result = tmdbApi.searchMovies(apiKey, query)
                movies.value = result.results
                Log.d("MoviesSearch", "Found movies: ${result.results.size}")
            } catch (e: Exception) {
                Log.e("MoviesSearch", "Error searching movies", e)
            }
        }
    }

    // Rechercher des séries par mot-clé
    fun searchTvShows(query: String) {
        viewModelScope.launch {
            try {
                val result = tmdbApi.searchTv(query, apiKey)
                tvShows.value = result.results
                Log.d("TvSearch", "Found TV shows: ${result.results.size}")
            } catch (e: Exception) {
                Log.e("TvSearch", "Error searching TV shows", e)
            }
        }
    }

    // Rechercher des acteurs par mot-clé
    fun searchActors(query: String) {
        viewModelScope.launch {
            try {
                val result = tmdbApi.searchActeur(query, apiKey)
                actors.value = result.results
                Log.d("ActorsSearch", "Found actors: ${result.results.size}")
            } catch (e: Exception) {
                Log.e("ActorsSearch", "Error searching actors", e)
            }
        }
    }
}
