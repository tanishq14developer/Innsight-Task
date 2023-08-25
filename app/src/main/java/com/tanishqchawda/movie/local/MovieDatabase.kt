package com.tanishqchawda.movie.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.tanishqchawda.movie.model.Search


@Database(
    entities = [Search::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(MoviesConverter::class)
abstract class MovieDatabase:RoomDatabase() {

    abstract fun movieDao(): MovieDao
    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie_database"
            )
                .build()
        }
    }



}