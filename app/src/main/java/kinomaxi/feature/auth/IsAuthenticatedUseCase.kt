package kinomaxi.feature.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsAuthenticatedUseCase @Inject constructor(
    private val dataStoreRepository: AuthDataStore
) {
    operator fun invoke(): Flow<Boolean> =
        dataStoreRepository.sessionPreferencesFlow
            .map { !it.isNullOrEmpty() }
}