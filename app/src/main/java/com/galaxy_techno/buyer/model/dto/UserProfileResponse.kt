package com.galaxy_techno.buyer.model.dto

data class UserProfileResponse (
    val success: Boolean,
    val error: Error,
    val data: Data
) {
    class Data(
        val accessID: String,
        val userID: String,
        val userName: String,
        val photo: String,
        val backgroundphoto: String
    )
}