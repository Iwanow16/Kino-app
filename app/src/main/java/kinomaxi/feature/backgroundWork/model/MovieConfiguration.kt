package kinomaxi.feature.backgroundWork.model

data class MovieConfiguration(
    val images: ImageConfiguration,
    val changeKeys: List<String>,
)

data class ImageConfiguration(
    val baseUrl: String,
    val secureBaseUrl: String,
    val backdropSizes: List<String>,
    val logoSizes: List<String>,
    val posterSizes: List<String>,
    val profileSizes: List<String>,
    val stillSizes: List<String>,
)