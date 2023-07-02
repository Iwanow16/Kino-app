package kinomaxi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.app.App
import kinomaxi.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navigator = AppNavigator(this, R.id.container)

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)

        supportFragmentManager.addOnBackStackChangedListener {
            val showBackButton = supportFragmentManager.backStackEntryCount > 0
            supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
            supportActionBar?.setDisplayShowHomeEnabled(!showBackButton)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.INSTANCE.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!super.onSupportNavigateUp()) {
            onBackPressedDispatcher.onBackPressed()
        }
        return true
    }
}
