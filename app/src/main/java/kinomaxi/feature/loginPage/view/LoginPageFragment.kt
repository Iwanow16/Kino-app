package kinomaxi.feature.loginPage.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import kinomaxi.R
import kinomaxi.databinding.FragmentLoginPageBinding

class LoginPageFragment: Fragment(R.layout.fragment_login_page) {

    private val viewBinding: FragmentLoginPageBinding by viewBinding(FragmentLoginPageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = viewBinding.username
        val password = viewBinding.password

        viewBinding.loginButton.setOnClickListener {
            if (username.length() > 0 && password.length() > 0) {

            }
        }
    }
}