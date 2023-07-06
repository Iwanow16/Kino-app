package kinomaxi.feature.loginPage.domain

import kinomaxi.feature.loginPage.data.LoginApiService
import kinomaxi.feature.loginPage.data.RestBodySessionLogin
import kinomaxi.feature.loginPage.data.RestRequestToken
import javax.inject.Inject

class GetSessionIdUseCase @Inject constructor(
    private val apiService: LoginApiService
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
        return apiService.createSession(RestRequestToken(requestToken)).sessionId
    }
}