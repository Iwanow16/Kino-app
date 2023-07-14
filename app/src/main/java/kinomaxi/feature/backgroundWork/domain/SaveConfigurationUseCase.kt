package kinomaxi.feature.backgroundWork.domain

import kinomaxi.feature.backgroundWork.data.ConfDataStore
import kinomaxi.feature.backgroundWork.data.ConfigurationApiService
import kinomaxi.feature.backgroundWork.data.RestConfiguration
import kinomaxi.feature.backgroundWork.model.ImageConfiguration
import kinomaxi.feature.backgroundWork.model.MovieConfiguration
import javax.inject.Inject

class SaveConfigurationUseCase @Inject constructor(
    private val apiService: ConfigurationApiService,
    private val dataStore: ConfDataStore
) {

    suspend operator fun invoke() {
        val conf = apiService.getConfiguration().toEntity()
        dataStore.saveConfiguration(
            conf.images.secureBaseUrl,
            conf.images.backdropSizes[0],
            conf.images.posterSizes[3],
        )
    }
}


private fun RestConfiguration.toEntity(): MovieConfiguration =
    MovieConfiguration(
        images = ImageConfiguration(
            images.baseUrl,
            images.secureBaseUrl,
            images.backdropSizes,
            images.logoSizes,
            images.posterSizes,
            images.profileSizes,
            images.stillSizes
        ),
        changeKeys = changeKeys
    )