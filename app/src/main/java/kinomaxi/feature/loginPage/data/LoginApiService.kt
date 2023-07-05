package kinomaxi.feature.loginPage.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(): RestGuestSessionId

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RestRequestToken

    @POST("authentication/token/validate_with_login")
    suspend fun confirmSessionWithLogin(@Body restBody: RestBody)

    @POST("authentication/session/new")
    suspend fun createSession(@Body restBodyRequestToken: RestRequestToken): RestSessionId
}
