package kinomaxi.feature.accountDetails.data

import kinomaxi.feature.auth.AuthDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val INTERCEPT_CODE = 401

class SessionExpiredInterceptor @Inject constructor(
    private val authDataStore: AuthDataStore
): Interceptor {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == INTERCEPT_CODE) {
            scope.launch { authDataStore.removeSessionId() }
        }
        return response
    }
}