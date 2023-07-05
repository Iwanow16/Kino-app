package kinomaxi.feature.loginPage.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.databinding.FragmentLoginPageBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginPageFragment : DialogFragment(R.layout.fragment_login_page) {

    private val viewBinding: FragmentLoginPageBinding by viewBinding(FragmentLoginPageBinding::bind)

    private val viewModel: LoginPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT

        viewBinding.loginButton.setOnClickListener {
            val username = viewBinding.username.text.toString()
            val password = viewBinding.password.text.toString()
            viewModel.createSession(username, password)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect {
                    showNewState(it)
                }
            }
        }
    }

    private fun showNewState(state: LoginPageViewState) {
        when(state) {
            LoginPageViewState.Loading -> {
                viewBinding.errorText.visibility = View.INVISIBLE
            }
            LoginPageViewState.Error -> {
                viewBinding.errorText.visibility = View.VISIBLE
            }
            is LoginPageViewState.Success -> {
                viewBinding.errorText.visibility = View.INVISIBLE
                dialog?.dismiss()
            }
        }
    }
}