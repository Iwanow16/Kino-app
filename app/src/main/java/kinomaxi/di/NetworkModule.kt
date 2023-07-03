package kinomaxi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieList.data.MoviesListApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhODY3ZDkwNWY1OTNjMTU2MGRlMDkyOWQ0ZTExZTZlNyIsInN1YiI6IjY0YTI5ZWQyMTEzODZjMDEzOWFlMmQ1MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bzH9FNNjkSmPAx5QCfCF5NqUgEhwIz4OwsQzVfpUlxo"
    private val baseURL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("language", "ru")
                    .build()
                val request = chain.request().newBuilder()
                    .url(url)
                    .header("Authorization", "Bearer $bearerToken")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesListApiService(retrofit: Retrofit): MoviesListApiService {
        return retrofit.create(MoviesListApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailApiService(retrofit: Retrofit): MovieDetailsApiService {
        return retrofit.create(MovieDetailsApiService::class.java)
    }
}