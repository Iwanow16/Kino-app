package kinomaxi.feature.loginPage.domain

import kinomaxi.feature.loginPage.data.LoginApiService
import kinomaxi.feature.loginPage.data.RestRequestToken
import javax.inject.Inject

class GetRequestTokenUseCase @Inject constructor(
    private val apiService: LoginApiService
) {
    suspend operator fun invoke(): String {
        return apiService.getRequestToken().requestToken
    }
}