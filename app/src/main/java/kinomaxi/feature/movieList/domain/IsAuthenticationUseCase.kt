package kinomaxi.feature.movieList.domain

import kinomaxi.feature.authFeature.AuthDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsAuthenticationUseCase @Inject constructor(
    private val dataStoreRepository: AuthDataStore
) {
    operator fun invoke(): Flow<String?> =
        dataStoreRepository.sessionPreferencesFlow
}