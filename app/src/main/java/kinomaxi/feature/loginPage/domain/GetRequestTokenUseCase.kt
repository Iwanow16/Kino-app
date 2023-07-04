package kinomaxi.feature.loginPage.domain

import kinomaxi.feature.loginPage.data.LoginApiService
import javax.inject.Inject

class GetRequestTokenUseCase @Inject constructor(
    private val apiService: LoginApiService
){
    operator fun invoke(): String {
        return apiService.getRequestToken().toString()
    }
}