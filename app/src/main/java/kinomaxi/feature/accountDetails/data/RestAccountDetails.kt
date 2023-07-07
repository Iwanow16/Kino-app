package kinomaxi.feature.accountDetails.data

import com.google.gson.annotations.SerializedName

data class RestAccountDetails(
    @SerializedName("avatar") val avatar: RestAccountAvatar,
    @SerializedName("id") val id: Long,
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("name") val name: String,
    @SerializedName("include_adult") val includeAdult: Boolean,
    @SerializedName("username") val username: String,
)

data class RestAccountAvatar(
    @SerializedName("gravatar") val gravatar: RestHash,
    @SerializedName("tmdb") val tmdb_avatar: RestAvatarPath,
)

data class RestHash(
    @SerializedName("hash") val hash: String,
)

data class RestAvatarPath(
    @SerializedName("avatar_path") val avatar_path: String?,
)