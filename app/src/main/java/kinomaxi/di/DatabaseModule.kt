package kinomaxi.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kinomaxi.feature.database.AppDatabase
import kinomaxi.feature.favorites.data.FavoriteMovieDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "appDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(
        appDatabase: AppDatabase
    ): FavoriteMovieDao {
        return appDatabase.favoriteMovieDao()
    }
}