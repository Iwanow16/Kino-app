package kinomaxi.feature.authFeature

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

private const val SESSION_ID_KEY = "SESSION_ID_KEY"

private object PreferencesKeys {
    val SESSION_ID = stringPreferencesKey("session_id")
}

@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SESSION_ID_KEY)
    private val dataStore = context.dataStore

    val sessionPreferencesFlow: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.SESSION_ID]
        }

    suspend fun saveSessionId(sessionId: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SESSION_ID] = sessionId
        }
    }

    suspend fun removeSessionId() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.SESSION_ID)
        }
    }
}