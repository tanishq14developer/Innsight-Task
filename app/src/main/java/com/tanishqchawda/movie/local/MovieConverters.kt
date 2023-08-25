package com.tanishqchawda.movie.local

import androidx.room.TypeConverter
import java.util.Date

class MoviesConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {

        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}