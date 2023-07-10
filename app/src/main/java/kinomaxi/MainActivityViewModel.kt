package kinomaxi

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.auth.IsAuthenticatedUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    isAuthenticatedUseCase: IsAuthenticatedUseCase
) : ViewModel() {

    val isAuth: Flow<Boolean> = isAuthenticatedUseCase()
}