package kinomaxi.feature.accountDetails.view

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.databinding.FragmentAccountDetailsBinding
import kinomaxi.feature.accountDetails.compose.AccountDetailsPageCompose
import javax.inject.Inject

@AndroidEntryPoint
class AccountDetailsFragment : Fragment(R.layout.fragment_account_details) {

    @Inject
    lateinit var router: Router

    private val viewBinding: FragmentAccountDetailsBinding by viewBinding(
        FragmentAccountDetailsBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AccountDetailsPageCompose()
            }
        }
    }
}