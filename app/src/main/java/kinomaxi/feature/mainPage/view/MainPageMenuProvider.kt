package kinomaxi.feature.mainPage.view

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.Router
import kinomaxi.R
import kinomaxi.Screens.AccountScreen
import kinomaxi.feature.loginPage.view.LoginPageFragment

class MainPageMenuProvider(
    private val router: Router,
    private val childFragmentManager: FragmentManager
    ): MenuProvider {

    private var loginButton: MenuItem? = null
    private var accountButton: MenuItem? = null

    private var isAuth: Boolean = false

    fun updateMenu(isUserAuth: Boolean) {
        Log.d("TEST", isUserAuth.toString())
        loginButton?.isVisible = !isUserAuth
        accountButton?.isVisible = isUserAuth
        isAuth = isUserAuth
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        Log.d("TEST", "onCreateMenu")
        menuInflater.inflate(R.menu.menu, menu)
        loginButton = menu.findItem(R.id.button_login)
        accountButton = menu.findItem(R.id.button_account)
        updateMenu(isAuth)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.button_login -> {
                //router.navigateTo(LoginScreen())
                LoginPageFragment().show(childFragmentManager, "Login")
                true
            }

            R.id.button_account -> {
                router.navigateTo(AccountScreen())
                true
            }

            else -> false
        }
}