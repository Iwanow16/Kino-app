package kinomaxi.dataStore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val SESSION_ID_KEY = "SESSION_ID_KEY"

private object PreferencesKeys {
    val SESSION_ID = stringPreferencesKey("session_id")
}

class DataStoreRepository @Inject constructor(
    context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SESSION_ID_KEY)
    private val dataStore = context.dataStore

    val sessionPreferencesFlow: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val sessionId = preferences[PreferencesKeys.SESSION_ID] ?: ""
            sessionId
        }

    suspend fun getSessionId() {
        dataStore.data.collect {preferences ->
            val sessionId: String? = preferences[PreferencesKeys.SESSION_ID]
            Log.d("getSessionId", sessionId.toString())
        }
    }

    suspend fun setSessionId(sessionId: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SESSION_ID] = sessionId
        }
    }
}