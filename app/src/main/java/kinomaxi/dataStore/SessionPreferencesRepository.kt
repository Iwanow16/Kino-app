package kinomaxi.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

data class StingPreferences(val sessionId: String)

private const val SESSION_PREFERENCES_NAME = "session_preferences"

private val Context.dataStore by preferencesDataStore(
    name = SESSION_PREFERENCES_NAME
)

private object PreferencesKeys {
    val SESSION_ID = stringPreferencesKey("session_id")
}

class SessionPreferencesRepository(
    private val dataStore: DataStore<Preferences>,
    context: Context
) {

    val userPreferencesFlow: Flow<StingPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val showCompleted = preferences[PreferencesKeys.SESSION_ID]?: ""
            StingPreferences(showCompleted)
        }

    suspend fun updateShowCompleted(sessionId: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SESSION_ID] = sessionId
        }
    }
}