package com.example.monprofil

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    //films à l'affiche
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") apikey: String): LastMovie

    //recherche de films
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): LastMovie

    //requête séries à l'affiche
    @GET("trending/tv/week")
    suspend fun lasttv(@Query("api_key") api_key: String): LastTv

    //requête recherche de series
    @GET("search/tv")
    suspend fun searchTv(
        @Query("query") query: String,
        @Query("api_key") api_key: String, ): LastTv

    //requête acteurs populaires
    @GET("trending/person/week")
    suspend fun lastacteur(@Query("api_key") api_key: String): LastActeur

    //requête recherche d'acteurs
    @GET("search/person")
    suspend fun searchActeur(@Query("query") query: String,
                             @Query("api_key") api_key: String): LastActeur


    @GET("movie/{movie_id}/credits")
    suspend fun getActorsOfMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Distribution

    @GET("tv/{tv_id}/credits")
    suspend fun getActorsOfTv(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ): Distribution
}