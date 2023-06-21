package kinomaxi

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setTitle(text: String?) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = text
}

fun Fragment.setSubtitle(text: String?) {
    (activity as? AppCompatActivity)?.supportActionBar?.subtitle = text
}

fun Fragment.navigateTo(fragment: Fragment) {
    (activity as? MainActivity)?.showFragment(fragment)
}
