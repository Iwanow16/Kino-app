package kinomaxi.feature.movieList.domain

import kinomaxi.feature.authFeature.AuthDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsAuthenticatedUseCase @Inject constructor(
    private val dataStoreRepository: AuthDataStore
) {
    operator fun invoke(): Flow<Boolean> =
        dataStoreRepository.sessionPreferencesFlow
            .map { it.isNullOrEmpty() }
}