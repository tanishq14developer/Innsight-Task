package com.tanishqchawda.movie.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tanishqchawda.movie.local.MovieDatabase
import com.tanishqchawda.movie.local.MovieRepository

import com.tanishqchawda.movie.model.Search
import com.tanishqchawda.movie.network.ApiEndPoints
import com.tanishqchawda.movie.paging.PagingSourceForPaginatedResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.asLiveData
import androidx.paging.liveData


@HiltViewModel
class BeerViewModel @Inject constructor(
    private val application: Application, private val apiInterFace: ApiEndPoints
) : ViewModel() {


    private val movieRepository: MovieRepository

    val beerLiveData: LiveData<PagingData<Search>>
    private val _responseSearch: MutableLiveData<List<Search>?> = MutableLiveData()
    val responseSearch: LiveData<List<Search>?>
        get() = _responseSearch


    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        movieRepository = MovieRepository(movieDao)

        // Collect beer data as LiveData
        beerLiveData = getBeer().cachedIn(viewModelScope).asLiveData()

        // Collect cached data from Room as LiveData


        viewModelScope.launch {
            val orderHistory = movieRepository.getMovie()
            if (orderHistory.isEmpty()) {
                // Start fetching beer data
                getBeer().collect()
            } else {
                _responseSearch.value = getCachedData()
            }
        }
    }

    private fun getBeer(): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                PagingSourceForPaginatedResponse(
                    apiInterFace
                )
            }
        ).flow
    }

    private fun getBeerLive (){
        viewModelScope.launch {
            val pager = Pager(
                config = PagingConfig(pageSize = 10, prefetchDistance = 2),
                pagingSourceFactory = {
                    PagingSourceForPaginatedResponse(
                        apiInterFace
                    )
                }
            )
            pager.liveData.cachedIn(viewModelScope)
        }
    }

    suspend fun getCachedData(): List<Search> {
        return movieRepository.getMovie()
    }
}
