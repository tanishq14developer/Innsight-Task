package com.tanishqchawda.movie.model

data class GenericPaginatedResponse<out T>(
    val data:List<T>
)