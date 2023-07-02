package kinomaxi

import com.github.terrakok.cicerone.androidx.FragmentScreen
import kinomaxi.feature.favorites.view.FavoritesFragment
import kinomaxi.feature.mainPage.view.MainPageFragment
import kinomaxi.feature.movieDetails.view.MovieDetailsFragment


object Screens {
    fun favorites() = FragmentScreen {
        FavoritesFragment()
    }

    fun mainPage() = FragmentScreen {
        MainPageFragment()
    }

    fun movieDetails(movieId: Long) = FragmentScreen {
        MovieDetailsFragment.getInstance(movieId)
    }
}