package kinomaxi

import com.github.terrakok.cicerone.androidx.FragmentScreen
import kinomaxi.feature.favorites.view.FavoritesFragment
import kinomaxi.feature.loginPage.view.LoginPageFragment
import kinomaxi.feature.movieDetails.view.MovieDetailsFragment


object Screens {
    fun FavoriteScreen() = FragmentScreen {
        FavoritesFragment()
    }

    fun DetailsScreen(movieId: Long) = FragmentScreen {
        MovieDetailsFragment.getInstance(movieId)
    }

    fun LoginScreen() = FragmentScreen {
        LoginPageFragment()
    }
}