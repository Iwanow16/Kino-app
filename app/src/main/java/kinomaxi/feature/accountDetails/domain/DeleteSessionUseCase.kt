package kinomaxi.feature.accountDetails.domain

import kinomaxi.feature.accountDetails.data.AccountDetailsApiServers
import javax.inject.Inject

class DeleteSessionUseCase  @Inject constructor(
    private val apiServers: AccountDetailsApiServers
) {
    suspend operator fun invoke(sessionID: String) {
        apiServers.deleteSession(sessionID)
    }
}