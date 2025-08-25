package com.example.moviesapi.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviesapi.data.local.converters.Converters
import com.example.moviesapi.domain.model.MoviesDomain

@Entity(tableName = "movies")
@TypeConverters(Converters::class)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val description: String,
    val genre: List<String>,
    val isFavorite: Boolean = false,
    val poster: String,
    val rating: Double,
    val title: String,
    val year: Int
)

fun MovieEntity.toMoviesDomain(): MoviesDomain{
    return MoviesDomain(
        description = description,
        id = id,
        isFavorite = isFavorite,
        poster = poster,
        title = title,
        year = year,
        genre = genre,
        rating = rating,
    )
}

fun MoviesDomain.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        description = description,
        genre = genre,
        isFavorite = isFavorite,
        poster = poster,
        rating = rating,
        title = title,
        year = year
    )
}