package com.tanishqchawda.movie

import android.app.Application
import com.tanishqchawda.movie.local.MovieDatabase
import com.tanishqchawda.movie.local.MovieRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp:Application() {

    private val database by lazy { MovieDatabase.getDatabase(this) }
    val repository by lazy { MovieRepository(database.movieDao()) }



}
