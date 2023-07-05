package kinomaxi.feature.loginPage.data

import com.google.gson.annotations.SerializedName

data class RestRequestToken(
    @SerializedName("request_token") val requestToken: String
)

data class RestSessionId(
    @SerializedName("session_id") val sessionId: String
)

data class RestGuestSessionId(
    @SerializedName("guest_session_id") val guestSessionId: String
)

data class RestBodySessionLogin(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("request_token") val requestToken: String
)