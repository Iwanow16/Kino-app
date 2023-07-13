package kinomaxi.feature.accountDetails.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kinomaxi.R
import kinomaxi.feature.accountDetails.model.AccountDetails
import kinomaxi.feature.accountDetails.view.AccountDetailsViewModel
import kinomaxi.feature.accountDetails.view.AccountDetailsViewState
import kinomaxi.feature.movieList.compose.ErrorViewCompose
import kinomaxi.feature.movieList.compose.LoaderViewCompose

@Composable
fun AccountDetailsPageCompose(
    accountViewModel: AccountDetailsViewModel = viewModel()
) {
    val state by accountViewModel.viewState.collectAsState()

    when (state) {
        AccountDetailsViewState.Error ->
            ErrorViewCompose { accountViewModel.refreshData() }

        AccountDetailsViewState.Loading ->
            LoaderViewCompose()

        is AccountDetailsViewState.Success -> AccountDetailsLayout(
            (state as AccountDetailsViewState.Success).accountDetails
        ) { accountViewModel.removeSession() }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AccountDetailsLayout(
    accountDetails: AccountDetails,
    removeSession: () -> Unit
) {
    Column {
        Row {
            GlideImage(
                model = accountDetails.avatar.avatarPath,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Account image",
                modifier = Modifier
                    .absolutePadding(16.dp, 16.dp)
                    .fillMaxWidth(0.35f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(25.dp))
            )
            Column(
                Modifier.absolutePadding(8.dp, 16.dp, 16.dp, 8.dp)
            ) {
                Text(
                    text = accountDetails.username,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
                Row {
                    Text(
                        text = stringResource(id = R.string.account_country_description),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    )
                    Text(
                        text = accountDetails.country,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { removeSession() }) {
                Text(
                    text = stringResource(id = R.string.account_remove_session_button),
                )
            }
        }
    }
}