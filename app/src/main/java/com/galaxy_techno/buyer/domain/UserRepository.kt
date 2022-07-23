package com.galaxy_techno.buyer.domain

import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.model.dto.*
import com.galaxy_techno.buyer.model.entity.User
import com.galaxy_techno.buyer.model.req.CategoryListRequest
import com.galaxy_techno.buyer.model.req.UserProfileRequest
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun requestOTP(
        mobile: String
    ): Flow<UiState<OTPRequestDTO>>

    suspend fun validateOTP(
        mobile: String,
        otpCode: String
    ): Flow<UiState<OTPValidateDTO>>

    suspend fun register(
        name: String,
        email: String,
        gender: String,
        mobile: String,
        password: String
    ): Flow<UiState<RegisterDTO>>

    suspend fun login(
        mobile: String,
        password: String
    ): Flow<UiState<LoginDTO>>

    suspend fun saveUser(
        user: User
    )

    suspend fun registerFavouriteCategory(
        body: CategoryListRequest
    ): Flow<UiState<FavouriteCategoryDTO>>

    suspend fun userProfileInfo(
        authorization: String ,
        request : UserProfileRequest
    ): Flow<UiState<UserProfileResponse>>

    suspend fun getUser(): Flow<User?>

    suspend fun deleteUser()
}