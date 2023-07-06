package kinomaxi.feature.accountDetails.data

import retrofit2.http.GET

interface AccountDetailsApiServers {

    @GET("account")
    suspend fun getAccountDetails(): RestAccountDetails
}