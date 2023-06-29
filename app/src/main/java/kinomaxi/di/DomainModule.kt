package kinomaxi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.domain.GetMovieDetailsUseCase
import kinomaxi.feature.movieDetails.domain.GetMovieImagesUseCase
import kinomaxi.feature.movieDetails.domain.IsMovieFavoriteFlow
import kinomaxi.feature.movieList.data.MoviesListApiService
import kinomaxi.feature.movieList.domain.GetMoviesListUseCase


@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetMovieDetailsUseCase(
        apiService: MovieDetailsApiService,
        favoriteMoviesRepository: FavoriteMoviesRepository
    ): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(apiService, favoriteMoviesRepository)
    }

    @Provides
    fun provideGetMovieImagesUseCase(apiService: MovieDetailsApiService): GetMovieImagesUseCase {
        return GetMovieImagesUseCase(apiService)
    }

    @Provides
    fun provideIsMovieFavoriteFlow(favoriteMoviesRepository: FavoriteMoviesRepository): IsMovieFavoriteFlow {
        return IsMovieFavoriteFlow(favoriteMoviesRepository)
    }

    @Provides
    fun provideGetMoviesListUseCase(apiService: MoviesListApiService): GetMoviesListUseCase {
        return GetMoviesListUseCase(apiService)
    }
}
