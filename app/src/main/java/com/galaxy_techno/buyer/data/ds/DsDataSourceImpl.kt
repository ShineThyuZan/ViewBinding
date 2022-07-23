package com.galaxy_techno.buyer.data.ds

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

open class DsDataSourceImpl @Inject constructor(
    private val ds: DataStore<Preferences>
) : DsDataSource {

    companion object {
        val AUTH_STATE = booleanPreferencesKey("com.galaxy_techno.auth_state")
        val TOKEN = stringPreferencesKey("com.galaxy_techno.token")
    }

    override suspend fun saveAuthState(isLoggedIn: Boolean) {
        ds.edit {
            it[AUTH_STATE] = isLoggedIn
        }
    }

    override suspend fun getAuthState(): Flow<Boolean> {
        return ds.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences()) else throw exception
            }.map {
                it[AUTH_STATE] ?: false
            }
    }
    override suspend fun saveToken(token: String) {
        ds.edit {
            it[TOKEN]= token
        }
    }

    override suspend fun getToken(): Flow<String> {
        return ds.data
            .catch { exception ->
                if(exception is IOException)emit(emptyPreferences()) else throw exception
            }.map {
                it[TOKEN] ?: "null"
            }
    }
}