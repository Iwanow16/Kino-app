package kinomaxi.feature.loginPage.domain

import android.util.Log
import kinomaxi.feature.loginPage.data.LoginApiService
import kinomaxi.feature.loginPage.data.RestBody
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
        apiService.confirmSessionWithLogin(RestBody(username, password, requestToken))
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