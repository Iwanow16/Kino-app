package kinomaxi.feature.movieList.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.compose.AppTheme
import kinomaxi.R
import kinomaxi.feature.mainPage.view.MainPageData
import kinomaxi.feature.mainPage.view.MainPageState
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesList
import kinomaxi.feature.movieList.model.MoviesListType

@Composable
fun MainPageCompose(
    state: MainPageState,
    onMovieClick: (movieId: Long) -> Unit,
    onFavoriteClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (state) {
                MainPageState.Loading -> {
                    LoaderViewCompose()
                }

                MainPageState.Error -> {
                    ErrorViewCompose { onRefreshClick() }
                }

                is MainPageState.Success -> {
                    MovieMainsListsCompose(state.data, onMovieClick)
                }
            }
            FavoriteButton(onFavoriteClick)
        }
    }
}

@Composable
fun FavoriteButton(onFavoriteClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            onClick = { onFavoriteClick() },
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_favorite_24),
                contentDescription = "Favorite icon",
            )
            Text(
                text = stringResource(id = R.string.favorite_movies_list_title)
            )
        }
    }
}

@Composable
fun MovieMainsListsCompose(data: MainPageData, onMovieClick: (movieId: Long) -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        MovieListCompose(data.topRatedMoviesList, onMovieClick)
        MovieListCompose(data.topPopularMoviesList, onMovieClick)
        MovieListCompose(data.topUpcomingMoviesList, onMovieClick)
    }
}

@Composable
fun MovieListCompose(movies: MoviesList, onMovieClick: (movieId: Long) -> Unit) {
    val listState = rememberLazyListState()
    val lazyPagingItems = movies.movies.flow.collectAsLazyPagingItems()

    val title = when (movies.type) {
        MoviesListType.TOP_RATED_MOVIES -> R.string.top_rated_title
        MoviesListType.POPULAR_MOVIES -> R.string.top_popular_title
        MoviesListType.UPCOMING_MOVIES -> R.string.top_upcoming_title
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp, 8.dp)
        )
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->
                val movieCard = lazyPagingItems[index]
                if (movieCard != null) {
                    MovieCardCompose(movie = movieCard, onMovieClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieCardCompose(movie: Movie, onMovieClick: (movieId: Long) -> Unit) {
    GlideImage(
        model = movie.posterUrl,
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = "movie poster",
        modifier = Modifier
            .aspectRatio(1/1.5f)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onMovieClick(movie.id) }
    )
}

