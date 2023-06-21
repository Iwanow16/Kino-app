package kinomaxi.feature.favorites.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieList.model.Movie

class FavoritesViewModel(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {

    /** Список избранных фильмов */
    val favoriteMovies: List<Movie>
        get() = favoriteMoviesRepository.getFavoriteMovies()

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val favoriteMoviesRepository = FavoriteMoviesRepository.instance

                FavoritesViewModel(
                    favoriteMoviesRepository = favoriteMoviesRepository,
                )
            }
        }
    }
}
