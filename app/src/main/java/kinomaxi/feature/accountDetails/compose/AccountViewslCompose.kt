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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kinomaxi.feature.accountDetails.model.AccountDetails

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AccountDetailsViewCompose(accountDetails: AccountDetails) {
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
                    style = MaterialTheme.typography.headlineMedium
                )
                Row {
                    Text(
                        text = "Страна: ",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = accountDetails.country,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { }) {
                Text(text = "Удалить сессию")
            }
        }
    }
}