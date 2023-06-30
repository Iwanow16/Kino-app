package kinomaxi.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kinomaxi.createApiService
import kinomaxi.feature.favorites.data.AppDatabase
import kinomaxi.feature.favorites.data.FavoriteMovieDao
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieList.data.MoviesListApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMoviesListApiService() : MoviesListApiService {
        return createApiService()
    }

    @Provides
    @Singleton
    fun provideDetailApiService() : MovieDetailsApiService {
        return createApiService()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "appDatabase"
            ).build()
        }
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(@ApplicationContext context: Context) : FavoriteMovieDao {
        return provideDatabase(context).favoriteMovieDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteMoviesRepository(favoriteMovieDao: FavoriteMovieDao) : FavoriteMoviesRepository {
        return FavoriteMoviesRepository(favoriteMovieDao)
    }
}