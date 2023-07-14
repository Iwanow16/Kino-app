package kinomaxi.feature.mainPage.view

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.Screens.DetailsScreen
import kinomaxi.Screens.FavoriteScreen
import kinomaxi.databinding.FragmentMainPageBinding
import kinomaxi.feature.movieList.compose.MainPageCompose
import kinomaxi.setSubtitle
import kinomaxi.setTitle
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainPageFragment : Fragment(R.layout.fragment_main_page) {

    @Inject
    lateinit var router: Router

    private val viewBinding: FragmentMainPageBinding by viewBinding(FragmentMainPageBinding::bind)

    private val viewModel: MainPageViewModel by viewModels()

    private val menuProvider by lazy { MainPageMenuProvider(router, childFragmentManager) }

    private val onMovieClick = { movieId: Long -> router.navigateTo(DetailsScreen(movieId)) }
    private val onFavoriteClick = { router.navigateTo(FavoriteScreen()) }
    private val onRefreshClick = { viewModel.refreshData() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.viewState.collect(::showNewState) }
                launch { viewModel.isUserAuthenticated.collect(menuProvider::updateMenu) }
            }
        }
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showNewState(state: MainPageState) {
        setTitle(getString(R.string.app_name))
        setSubtitle(null)

        with(viewBinding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainPageCompose(state, onMovieClick, onFavoriteClick, onRefreshClick)
            }
        }
    }
}