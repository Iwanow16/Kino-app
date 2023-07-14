package kinomaxi.feature.favorites.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kinomaxi.feature.favorites.view.FavoritesViewModel
import kinomaxi.feature.movieList.compose.MovieCardCompose
import kinomaxi.feature.movieList.model.FavoriteMovie
import kinomaxi.feature.movieList.model.Movie

@Composable
fun FavoritePageCompose(
    favoriteViewModel: FavoritesViewModel = viewModel(),
    onMovieClick: (movieId: Long) -> Unit,
) {
    val favorites = favoriteViewModel.favoriteMovies

    MovieFavoriteListCompose(favorites, onMovieClick)
}
@Composable
fun MovieFavoriteListCompose(
    favorites: Pager<Int, FavoriteMovie>,
    onMovieClick: (movieId: Long) -> Unit
) {
    val gridState = rememberLazyGridState()
    val lazyPagingItems = favorites.flow.collectAsLazyPagingItems()

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            val movieCard = lazyPagingItems[index]
            if (movieCard != null) {
                MovieCardCompose(
                    movie = Movie(
                        movieCard.id,
                        movieCard.title,
                        movieCard.posterUrl
                    ), onMovieClick
                )
            }
        }
    }
}