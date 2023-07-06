package kinomaxi.feature.accountDetails.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.databinding.FragmentAccountDetailsBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountDetailsFragment: Fragment(R.layout.fragment_account_details) {

    @Inject
    lateinit var router: Router

    private val viewBinding: FragmentAccountDetailsBinding by viewBinding(FragmentAccountDetailsBinding::bind)
    private val viewModel: AccountDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.viewState.collect(::showNewState) }
            }
        }

        viewBinding.deleteSessionButton.setOnClickListener {
            viewModel.removeSession()
            router.exit()
        }
    }

    private fun showNewState(state: AccountDetailsViewState) {
        when (state) {
            AccountDetailsViewState.Loading -> with(viewBinding) {
                username.text = "loading"
            }
            AccountDetailsViewState.Error -> with(viewBinding) {
                username.text = "error"
            }
            is AccountDetailsViewState.Success -> with(viewBinding) {
                username.text = state.accountDetails.username
                Glide.with(this@AccountDetailsFragment)
                    .load(state.accountDetails.avatar?.avatarPath)
                    .into(avatar)
            }
        }
    }
}