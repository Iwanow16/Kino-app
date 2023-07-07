package kinomaxi.feature.accountDetails.domain

import kinomaxi.feature.accountDetails.data.AccountDetailsApiServers
import kinomaxi.feature.accountDetails.data.RestSessionIdBody
import kinomaxi.feature.authFeature.AuthDataStore
import javax.inject.Inject

class DeleteSessionUseCase @Inject constructor(
    private val apiServers: AccountDetailsApiServers,
    private val dataStore: AuthDataStore
) {
    suspend operator fun invoke() {
        dataStore.sessionPreferencesFlow.collect {
            apiServers.deleteSession(RestSessionIdBody(it.toString()))
            dataStore.removeSessionId()
        }
    }
}