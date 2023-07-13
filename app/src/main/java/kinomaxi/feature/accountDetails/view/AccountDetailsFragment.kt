package kinomaxi.feature.accountDetails.view

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
import kinomaxi.databinding.FragmentAccountDetailsBinding
import kinomaxi.feature.accountDetails.compose.AccountDetailsViewCompose
import kinomaxi.feature.movieList.compose.ErrorViewCompose
import kinomaxi.feature.movieList.compose.LoaderViewCompose
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountDetailsFragment : Fragment(R.layout.fragment_account_details) {

    @Inject
    lateinit var router: Router

    private val viewBinding: FragmentAccountDetailsBinding by viewBinding(
        FragmentAccountDetailsBinding::bind
    )
    private val viewModel: AccountDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.viewState.collect(::showNewState) }
            }
        }

 /*       viewBinding.removeSessionButton.setOnClickListener {
            viewModel.removeSession()
        }*/
    }

    private fun showNewState(state: AccountDetailsViewState) {
        with(viewBinding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                when (state) {
                    AccountDetailsViewState.Loading -> {
                        LoaderViewCompose()
                    }

                    AccountDetailsViewState.Error -> {
                        ErrorViewCompose()
                    }

                    is AccountDetailsViewState.Success -> {
                        AccountDetailsViewCompose(accountDetails = state.accountDetails)
                    }
                }
            }
        }
    }
}

