package kinomaxi.feature.backgroundWork.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val CONFIGURATION_ID_KEY = "CONFIGURATION_ID_KEY"

private object PreferencesKeys {
    val BASE_URL = stringPreferencesKey("base_url_id")
    val BACKDROP_SIZE = stringPreferencesKey("backdrop_size_id")
    val POSTER_SIZE = stringPreferencesKey("poster_size_id")
}

@Singleton
class ConfDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = CONFIGURATION_ID_KEY)
    private val dataStore = context.dataStore

    val baseUrlConfigurationFlow: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.BASE_URL]
        }

    val backdropSizeConfigurationFlow: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.BACKDROP_SIZE]
        }

    val posterSizeConfigurationFlow: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.POSTER_SIZE]
        }

    suspend fun saveConfiguration(baseUrl: String, backdropSize: String, posterSize: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BASE_URL] = baseUrl
            preferences[PreferencesKeys.BACKDROP_SIZE] = backdropSize
            preferences[PreferencesKeys.POSTER_SIZE] = posterSize
        }
    }
}