package kinomaxi.feature.favorites.view

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.Screens.DetailsScreen
import kinomaxi.databinding.FragmentFavoritesBinding
import kinomaxi.feature.favorites.compose.FavoritePageCompose
import kinomaxi.feature.movieList.model.Banner
import kinomaxi.feature.movieList.model.FavoriteMovie
import kinomaxi.feature.movieList.view.MovieListItem
import kinomaxi.setSubtitle
import kinomaxi.setTitle
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    @Inject
    lateinit var router: Router

    private val viewBinding: FragmentFavoritesBinding by viewBinding(FragmentFavoritesBinding::bind)

    private val onMovieClick = { movieId: Long -> router.navigateTo(DetailsScreen(movieId)) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.favorite_movies_list_title))
        setSubtitle(null)

        with(viewBinding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                FavoritePageCompose(onMovieClick = onMovieClick)
            }
        }
    }
}

private fun FavoriteMovie.toViewData() =
    MovieListItem.Movie(id, posterUrl)

private fun Banner.toViewData() =
    MovieListItem.Banner(text)