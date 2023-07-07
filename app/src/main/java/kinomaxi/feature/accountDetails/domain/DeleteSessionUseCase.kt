package kinomaxi.feature.accountDetails.domain

import kinomaxi.feature.accountDetails.data.AccountDetailsApiServers
import kinomaxi.feature.accountDetails.data.RestSessionIdBody
import kinomaxi.feature.auth.AuthDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteSessionUseCase @Inject constructor(
    private val apiServers: AccountDetailsApiServers,
    private val dataStore: AuthDataStore
) {
    suspend operator fun invoke() {
        val sessionId = dataStore.sessionPreferencesFlow.first()
        apiServers.deleteSession(RestSessionIdBody(requireNotNull(sessionId)))
        dataStore.removeSessionId()
    }
}