package kinomaxi.feature.loginPage.domain

import kinomaxi.feature.auth.AuthDataStore
import kinomaxi.feature.loginPage.data.LoginApiService
import kinomaxi.feature.loginPage.data.RestBodySessionLogin
import kinomaxi.feature.loginPage.data.RestRequestToken
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val apiService: LoginApiService,
    private val dataStore: AuthDataStore
) {
    private suspend fun confirmSession(
        username: String,
        password: String,
        requestToken: String,
    ) {
        apiService.confirmSessionWithLogin(RestBodySessionLogin(username, password, requestToken))
    }

    suspend operator fun invoke(
        username: String,
        password: String,
        requestToken: String,
    ): String {
        confirmSession(username, password, requestToken)

        val sessionId = apiService.createSession(RestRequestToken(requestToken)).sessionId
        dataStore.saveSessionId(sessionId)

        return sessionId
    }
}