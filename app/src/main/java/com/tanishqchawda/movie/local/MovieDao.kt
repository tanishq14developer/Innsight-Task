package com.tanishqchawda.movie.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanishqchawda.movie.model.Search

@Dao
interface MovieDao {
    //OrderHistory
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovieList(creditHistory: List<Search>)

    @Query("SELECT * FROM search")
    suspend fun getMovieList(): List<Search>

    @Query("DELETE FROM search")
    suspend fun deleteMovieList()


}