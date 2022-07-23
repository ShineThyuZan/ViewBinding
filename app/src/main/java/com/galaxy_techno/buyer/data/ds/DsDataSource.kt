package com.galaxy_techno.buyer.data.ds

import kotlinx.coroutines.flow.Flow

interface DsDataSource {
    /** login? : not save and get state in(from) dataStore */
    suspend fun saveAuthState(isLoggedIn: Boolean)
    suspend fun getAuthState(): Flow<Boolean>

    /** access token and Refresh Token save and get state In(from) dataStore */
    suspend fun saveToken(token: String)
    suspend fun getToken(): Flow<String>
}