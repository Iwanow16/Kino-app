package kinomaxi.feature.favorites.view

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieList.model.Banner
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {

    /** Список баннеров */
    val banners: List<Banner>
        get() = listOf(
            Banner("This is a banner"),
        )

    /** Список избранных фильмов */
    val favoriteMovies: Flow<List<Movie>> = favoriteMoviesRepository.favoriteMovies
}
