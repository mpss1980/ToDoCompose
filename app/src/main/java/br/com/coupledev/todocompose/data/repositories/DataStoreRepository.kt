package br.com.coupledev.todocompose.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.coupledev.todocompose.data.DataConstants
import br.com.coupledev.todocompose.data.models.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataConstants.PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
){
    private object PreferencesKey {
        val sortKey = stringPreferencesKey(name = DataConstants.PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferencesKey.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
           if (exception is IOException) {
                emit(emptyPreferences())
                return@catch
            }
            throw exception
        }.map { preferences ->
            val sortState = preferences[PreferencesKey.sortKey] ?: Priority.NONE.name
            sortState
        }

}