package kinomaxi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val httpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val url = chain.request().url.newBuilder()
            .addQueryParameter("language", "ru")
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }
    .addInterceptor(HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    })
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .client(httpClient)
    .baseUrl(AppConfig.API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

inline fun <reified T> createApiService(): T {
    return retrofit.create(T::class.java)
}
