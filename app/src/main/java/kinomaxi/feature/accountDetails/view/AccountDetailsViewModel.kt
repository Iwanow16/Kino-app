package kinomaxi.feature.accountDetails.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.accountDetails.domain.GetAccountDetailsUseCase
import kinomaxi.feature.authFeature.AuthDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val dataStoreRepository: AuthDataStore
) : ViewModel() {

    private var _viewState =
        MutableStateFlow<AccountDetailsViewState>(AccountDetailsViewState.Loading)
    val viewState: Flow<AccountDetailsViewState> = _viewState.asStateFlow()
        .onSubscription { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            _viewState.value
        )

    fun removeSession() {
        viewModelScope.launch {
            dataStoreRepository.removeSessionId()
        }
    }

    fun refreshData() {
        _viewState.value = AccountDetailsViewState.Loading
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _viewState.value = AccountDetailsViewState.Success(getAccountDetailsUseCase())
            } catch (e: Exception) {
                _viewState.value = AccountDetailsViewState.Error
                Log.d("ERROR", e.toString())
            }
        }
    }
}