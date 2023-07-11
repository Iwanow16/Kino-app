package kinomaxi.feature.favorites.data

import kinomaxi.feature.movieList.data.RestMoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FavoriteApiService {

    @GET("account/account_id/favorite/movies")
    suspend fun getFavoriteList(@Query("page") page: Int): RestMoviesListResponse
}