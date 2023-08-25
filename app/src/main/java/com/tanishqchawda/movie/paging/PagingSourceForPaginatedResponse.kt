package com.tanishqchawda.movie.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

import com.tanishqchawda.movie.model.Search
import com.tanishqchawda.movie.network.ApiEndPoints

class PagingSourceForPaginatedResponse(
    private val apiRoutes : ApiEndPoints
):PagingSource<Int,Search>(){
    private  val TAG = "PagingSourceForPaginate"
    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return  try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val response = apiRoutes.getBeers(page)
            val data = response.body()?.response!!
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.isEmpty()) null else page + 1
            if(response.body()?.search.isNullOrEmpty())
            {
                return LoadResult.Page(data = emptyList(), prevKey =null,nextKey = null )
            }
            Log.e(TAG, "load: ${response.body()}", )
            LoadResult.Page(data = response.body()!!.search, prevKey =prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            Log.d("checkError", "loadMydata: ${e.message}")
            LoadResult.Error(e)
        }
    }

}
