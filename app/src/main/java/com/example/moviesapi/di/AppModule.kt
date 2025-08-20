package com.example.moviesapi.di

import android.app.Application
import androidx.room.Room
import com.example.moviesapi.core.Constants
import com.example.moviesapi.data.local.MoviesDatabase
import com.example.moviesapi.data.remote.MoviesApi
import com.example.moviesapi.data.repository.MoviesRepositoryImpl
import com.example.moviesapi.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesApi(): MoviesApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDatabase {
        return Room.databaseBuilder(
            app,
            MoviesDatabase::class.java,
            "movies_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(
        api: MoviesApi,
        database: MoviesDatabase
    ): MoviesRepository{
        return MoviesRepositoryImpl(api, database)
    }
}