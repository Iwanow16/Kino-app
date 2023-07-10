package kinomaxi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kinomaxi.feature.accountDetails.data.AccountDetailsApiServers
import kinomaxi.feature.accountDetails.data.ClientSessionInterceptor
import kinomaxi.feature.accountDetails.data.SessionExpiredInterceptor
import kinomaxi.feature.loginPage.data.LoginApiService
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

    private val bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZTAzMjdjNzY2N2ZhYTIzMDM1OTViNDkzZDJlMTU1MiIsInN1YiI6IjY0YTI5ZWQyMTEzODZjMDEzOWFlMmQ1MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MFCO0ej674suGNK6S5fq6vSnCsOMGR2u8cRoDGr8sY0"
    private val baseURL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideHttpClient(
        clientSessionInterceptor: ClientSessionInterceptor,
        sessionExpiredInterceptor: SessionExpiredInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("language", "ru")
                    .build()
                val request = chain.request().newBuilder()
                    .url(url)
                    .addHeader("Authorization", "Bearer $bearerToken")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .addInterceptor(sessionExpiredInterceptor)
            .addInterceptor(clientSessionInterceptor)
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
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
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

    @Provides
    @Singleton
    fun provideAccountDetailApiService(retrofit: Retrofit): AccountDetailsApiServers {
        return retrofit.create(AccountDetailsApiServers::class.java)
    }
}