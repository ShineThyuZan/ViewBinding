package com.galaxy_techno.buyer.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galaxy_techno.buyer.common.Constant
import com.galaxy_techno.buyer.model.entity.User

@Dao
interface UserDao {
    //todo : abstraction about Data Access Object
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM ${Constant.USER_TABLE}")
    suspend fun retrieveUser() : User

    @Query("DELETE FROM ${Constant.USER_TABLE}")
    suspend fun deleteUser()
}