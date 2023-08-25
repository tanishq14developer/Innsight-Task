package com.tanishqchawda.movie.local


import com.tanishqchawda.movie.model.Search

class MovieRepository(private val movieDao: MovieDao) {


    //MovieList
    suspend fun addMovieList(movieList: List<Search>) {
        movieDao.addMovieList(movieList)
    }
    suspend fun getMovie() : List<Search> = movieDao.getMovieList()

    suspend fun deleteOrderHistory() = movieDao.deleteMovieList()




}