package kinomaxi.feature.accountDetails.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP

interface AccountDetailsApiServers {

    @GET("account")
    suspend fun getAccountDetails(): RestAccountDetails

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(@Body restSessionIdBody: RestSessionIdBody)
}

data class RestSessionIdBody(
    @SerializedName("session_id") val sessionId: String
)