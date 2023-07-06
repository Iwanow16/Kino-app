package kinomaxi.feature.accountDetails.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.databinding.FragmentAccountDetailsBinding

@AndroidEntryPoint
class AccountDetailsFragment: Fragment(R.layout.fragment_account_details) {

    private val viewBinding: FragmentAccountDetailsBinding by viewBinding(FragmentAccountDetailsBinding::bind)

    private val viewModel: AccountDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.deleteSessionButton.setOnClickListener {

        }
    }
}