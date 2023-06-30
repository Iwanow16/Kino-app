package kinomaxi

import dagger.Component
import kinomaxi.di.LocalNavigationModule
import kinomaxi.di.NavigationModule
import kinomaxi.feature.favorites.view.FavoritesFragment
import kinomaxi.feature.mainPage.view.MainPageFragment
import kinomaxi.feature.movieDetails.view.MovieDetailsFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NavigationModule::class,
        LocalNavigationModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: MainPageFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: MovieDetailsFragment)
}