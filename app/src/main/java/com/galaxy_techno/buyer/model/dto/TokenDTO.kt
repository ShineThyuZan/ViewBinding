package com.galaxy_techno.buyer.model.dto

data class TokenDTO(
    val status: String,
    val messageCode: Int,
    val message: String,
    val data: RefreshTokenData
)

data class RefreshTokenData(
    val refreshToken: String
)