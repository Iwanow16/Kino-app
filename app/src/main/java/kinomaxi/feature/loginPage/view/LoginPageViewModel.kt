package kinomaxi.feature.loginPage.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.loginPage.domain.GetRequestTokenUseCase
import kinomaxi.feature.loginPage.domain.GetSessionIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginPageViewModel @Inject constructor(
    private val getRequestTokenUseCase: GetRequestTokenUseCase,
    private val getSessionIdUseCase: GetSessionIdUseCase,
) : ViewModel() {

    private var _viewState = MutableStateFlow<LoginPageViewState>(LoginPageViewState.Loading)
    val viewState: Flow<LoginPageViewState> = _viewState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            _viewState.value
        )

    fun createSession(username: String, password: String) {
        viewModelScope.launch {
            try {
                val requestToken = getRequestTokenUseCase()
                val sessionId = getSessionIdUseCase(username, password, requestToken)
                _viewState.value = LoginPageViewState.Success(sessionId)
            } catch (e: Exception) {
                _viewState.value = LoginPageViewState.Error
            }
        }
    }
}