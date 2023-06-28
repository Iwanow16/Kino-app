package kinomaxi.feature.favorites.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kinomaxi.feature.favorites.data.FavoriteDatabase
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieList.model.Banner
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.flow.Flow

class FavoritesViewModel(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {

    /** Список баннеров */
    val banners: List<Banner>
        get() = listOf(
            Banner("This is a banner"),
        )

    /** Список избранных фильмов */
    val favoriteMovies: Flow<List<Movie>> = favoriteMoviesRepository.favoriteMovies

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                val movieDao = FavoriteDatabase.getDatabase(application).movieDao()
                val favoriteMoviesRepository = FavoriteMoviesRepository(movieDao)

                FavoritesViewModel(
                    favoriteMoviesRepository = favoriteMoviesRepository,
                )
            }
        }
    }
}
