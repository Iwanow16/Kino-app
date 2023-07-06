package kinomaxi.feature.accountDetails.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET

interface AccountDetailsApiServers {

    @GET("account")
    suspend fun getAccountDetails(): RestAccountDetails

    @DELETE("authentication/session")
    suspend fun deleteSession(@Body sessionId: String)
}