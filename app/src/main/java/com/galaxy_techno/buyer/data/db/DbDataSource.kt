package com.galaxy_techno.buyer.data.db

import com.galaxy_techno.buyer.model.entity.User

interface DbDataSource {
    // TODO: abstraction Query to DB
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun deleteUser()
}