package kinomaxi.feature.accountDetails.data

import kinomaxi.feature.authFeature.AuthDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ClientSessionInterceptor @Inject constructor(
    authDataStore: AuthDataStore
) : Interceptor {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val sessionId: StateFlow<String?> = authDataStore.sessionPreferencesFlow
        .stateIn(
            scope,
            SharingStarted.Eagerly,
            "NO_SESSION_ID"
        )

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("session_id", sessionId.value)
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}