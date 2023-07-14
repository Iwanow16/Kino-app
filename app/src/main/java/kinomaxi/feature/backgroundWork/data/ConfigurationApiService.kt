package kinomaxi.feature.backgroundWork.data

import retrofit2.http.GET

interface ConfigurationApiService {

    @GET("configuration")
    suspend fun getConfiguration(): RestConfiguration

}

