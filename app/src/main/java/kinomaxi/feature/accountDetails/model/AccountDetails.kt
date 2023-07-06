package kinomaxi.feature.accountDetails.model

/**
 * Сущность детальной информации об аккаунте
 */
data class AccountDetails(
    val id: Long,
    val language: String,
    val country: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String,
    val avatar: AccountAvatar?
)

data class AccountAvatar(
    val hash: String,
    val avatarPath: String
)