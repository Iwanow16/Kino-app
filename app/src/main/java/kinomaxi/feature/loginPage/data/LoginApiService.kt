package kinomaxi.feature.loginPage.data

import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {

    @GET("authentication/token/new")
    fun getRequestToken(): String

    @GET("authentication/guest_session/new")
    fun createGuestSession(): String

    @POST("authentication/session/new")
    fun createSession(): String

    @POST("authentication/token/validate_with_login")
    fun createSessionWithLogin(): String
}
