package kinomaxi.feature.accountDetails.domain

import kinomaxi.AppConfig
import kinomaxi.feature.accountDetails.data.AccountDetailsApiServers
import kinomaxi.feature.accountDetails.data.RestAccountDetails
import kinomaxi.feature.accountDetails.model.AccountAvatar
import kinomaxi.feature.accountDetails.model.AccountDetails
import kinomaxi.feature.backgroundWork.data.ConfDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Бизнес-сценарий получения детальной информации об аккаунте
 */

class GetAccountDetailsUseCase @Inject constructor(
    private val apiServers: AccountDetailsApiServers,
    private val dataStore: ConfDataStore
) {
    suspend operator fun invoke(): AccountDetails {
        val baseUrl: String? = dataStore.baseUrlConfigurationFlow.first()
        return apiServers.getAccountDetails().toEntity(baseUrl)
    }
}

private fun RestAccountDetails.toEntity(baseUrl: String?): AccountDetails =
    AccountDetails(
        id = id,
        language = language,
        country = country,
        name = name,
        includeAdult = includeAdult,
        username = username,
        avatar = AccountAvatar(
            hash = "${AppConfig.GRAVATAR_URL}avatar/${avatar.gravatar.hash}?s=204&d=404",
            avatarPath = "${baseUrl}original${avatar.tmdbAvatar.avatarPath}"
        )
    )