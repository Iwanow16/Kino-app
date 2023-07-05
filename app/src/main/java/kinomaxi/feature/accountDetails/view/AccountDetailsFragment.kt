package kinomaxi.feature.accountDetails.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import kinomaxi.R
import kinomaxi.databinding.FragmentAccountDetailsBinding


class AccountDetailsFragment: Fragment(R.layout.fragment_account_details) {

    private val viewBinding: FragmentAccountDetailsBinding by viewBinding(FragmentAccountDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}