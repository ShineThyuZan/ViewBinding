package com.galaxy_techno.buyer.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.galaxy_techno.buyer.common.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constant.USER_TABLE)
data class User(
    @PrimaryKey(autoGenerate = false)
    val userId: Int?,
    val name: String,
    val email: String,
    val gender: String,
    val phone: String
) : Parcelable

