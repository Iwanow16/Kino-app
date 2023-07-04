package kinomaxi.feature.loginPage.data

import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {

    @GET("authentication/guest_session/new")
    fun createGuestSession(): RestGuestSessionId

    @GET("authentication/token/new")
    fun getRequestToken(): RestRequestToken

    @POST("authentication/token/validate_with_login")
    fun createSessionWithLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("request_token") requestToken: String
    )

    @POST("authentication/token/validate_with_login")
    fun createSession(
        @Field("request_token") requestToken: String
    ): RestSessionId
}
