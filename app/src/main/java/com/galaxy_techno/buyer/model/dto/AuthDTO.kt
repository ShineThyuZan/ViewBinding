package com.galaxy_techno.buyer.model.dto

import com.galaxy_techno.buyer.model.entity.User

data class LoginDTO(
    val status: String,
    val messageCode: Int,

    val data: UserData? = null,
    val message: String? = null,
    val error: String? = null
)

data class RegisterDTO(
    val status: String,
    val messageCode: Int,

    val data: UserData? = null,
    val message: String? = null,
    val error: String? = null
)

data class UserData(
    val accessToken : String,
    val refreshToken : String,
    val customerId: Int,
    val name: String,
    val email: String,
    val gender: String,
    val mobile: String,
    val password: String
)

fun UserData.toEntity(): User {
    return User(
        userId = customerId,
        name = name,
        email = email,
        gender = gender,
        phone = mobile
    )
}