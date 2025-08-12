package androidlead.weatherappui.ui.auth

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Extension on Context
private val Context.dataStore by preferencesDataStore(name = "session_prefs")

@Singleton
class SessionDataStore @Inject constructor(private val context: Context) {

    private object Keys {
        val LOGGED_IN = booleanPreferencesKey("logged_in")
        val USERNAME = stringPreferencesKey("username")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.LOGGED_IN] ?: false
    }

    val username: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.USERNAME]
    }

    suspend fun setLoggedIn(username: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.LOGGED_IN] = true
            prefs[Keys.USERNAME] = username
        }
    }

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs[Keys.LOGGED_IN] = false
            prefs[Keys.USERNAME] = ""
        }
    }
}
