package com.tanishqchawda.movie.network


import com.tanishqchawda.movie.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiEndPoints
{

    @GET("/")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("s") imdbId: String = "batman",
        @Query("apikey") apikey : String = Constants.APIKEY
    ):Response<MovieResponse>


}