package kinomaxi.feature.movieDetails.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.databinding.FragmentMovieDetailsBinding
import kinomaxi.databinding.LayoutErrorViewBinding
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage
import kinomaxi.setSubtitle
import kinomaxi.setTitle
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewBinding: FragmentMovieDetailsBinding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            errorView.setOnInflateListener { _, inflated ->
                with(LayoutErrorViewBinding.bind(inflated)) {
                    errorActionButton.setOnClickListener {
                        viewModel.refreshData()
                    }
                }
            }

            movieImagesView.adapter = MovieImagesAdapter()
            moviePosterLayout.favIcon.setOnClickListener {
                viewModel.toggleFavorites()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    showNewState(it)
                }
            }
        }
    }

    private fun showNewState(state: MovieDetailsViewState) {
        when (state) {
            MovieDetailsViewState.Loading -> with(viewBinding) {
                contentScrollView.isVisible = false
                loaderView.show()
                errorView.isVisible = false
            }

            MovieDetailsViewState.Error -> with(viewBinding) {
                contentScrollView.isVisible = false
                loaderView.hide()
                errorView.isVisible = true
            }

            is MovieDetailsViewState.Success -> with(viewBinding) {
                contentScrollView.isVisible = true
                loaderView.hide()
                errorView.isVisible = false
                showMovieDetails(state.movieDetails)
                showMovieImages(state.movieImages)
            }
        }
    }

    private fun FragmentMovieDetailsBinding.showMovieDetails(movie: MovieDetails) {
        setTitle(movie.title)
        setSubtitle(movie.originalTitle)

        moviePosterLayout.apply {
            val favoriteButtonIconResId = if (movie.isFavorite) {
                R.drawable.ic_favorite_24
            } else {
                R.drawable.ic_favorite_border_24
            }
            favIcon.icon = ContextCompat.getDrawable(requireContext(), favoriteButtonIconResId)

            movieRating.text = String.format("%.2f", movie.rating)
            Glide.with(this@MovieDetailsFragment)
                .load(movie.posterImage?.previewUrl)
                .into(moviePoster)
        }

        movieDetailsLayout.apply {
            movieGenres.text = movie.genres.joinToString(separator = ", ")
            movieTitle.text = movie.title
            movieTitleOriginal.text = movie.originalTitle
            movieTagline.text = movie.tagline
            movieReleaseDate.text = movie.releaseDate.format(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            )
            movieLength.text = getString(
                R.string.movie_length_value,
                movie.lengthMinutes / 60,
                movie.lengthMinutes % 60
            )
        }

        movieOverview.text = movie.overview
    }

    private fun FragmentMovieDetailsBinding.showMovieImages(movieImages: List<MovieImage>) {
        (movieImagesView.adapter as? MovieImagesAdapter)?.submitList(movieImages)
    }

    companion object {

        const val MOVIE_ID_ARG_KEY = "MOVIE_ID_KEY"

        fun getInstance(movieId: Long) = MovieDetailsFragment().apply {
            arguments = bundleOf(
                MOVIE_ID_ARG_KEY to movieId
            )
        }
    }
}
