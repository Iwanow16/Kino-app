package kinomaxi.feature.accountDetails.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

) : ViewModel() {

    private var _viewState =
        MutableStateFlow<AccountDetailsViewState>(AccountDetailsViewState.Loading)
    val viewState: Flow<AccountDetailsViewState> = _viewState.asStateFlow()
        .onSubscription { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            _viewState.value
        )

    fun refreshData() {
        _viewState.value = AccountDetailsViewState.Loading
        loadData()
    }

    private fun loadData() {

        viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
        }
    }

}