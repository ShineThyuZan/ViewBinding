package com.galaxy_techno.buyer.domain

import com.galaxy_techno.buyer.common.BaseDataSource
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.data.db.DbDataSource
import com.galaxy_techno.buyer.data.ds.DsDataSource
import com.galaxy_techno.buyer.data.remote.ApiDataSource
import com.galaxy_techno.buyer.di.Qualifier
import com.galaxy_techno.buyer.model.dto.*
import com.galaxy_techno.buyer.model.entity.User
import com.galaxy_techno.buyer.model.req.CategoryListRequest
import com.galaxy_techno.buyer.model.req.UserProfileRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class UserRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DbDataSource,
    private val dsDataSource: DsDataSource,
    @Qualifier.Io private val io: CoroutineDispatcher
) : UserRepository, BaseDataSource() {

    override suspend fun requestOTP(mobile: String): Flow<UiState<OTPRequestDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.requestOTP(mobile)
                }
            )
        }.flowOn(io)
    }

    override suspend fun validateOTP(
        mobile: String,
        otpCode: String
    ): Flow<UiState<OTPValidateDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.validateOTP(
                        mobile,
                        otpCode
                    )
                }
            )
        }.flowOn(io)
    }

    override suspend fun register(
        name: String,
        email: String,
        gender: String,
        mobile: String,
        password: String
    ): Flow<UiState<RegisterDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.register(
                        name,
                        email,
                        gender,
                        mobile,
                        password
                    )
                }
            )
        }.flowOn(io)
    }

    override suspend fun login(
        mobile: String,
        password: String
    ): Flow<UiState<LoginDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.login(
                        mobile,
                        password
                    )
                }
            )
        }.flowOn(io)
    }

    override suspend fun saveUser(user: User) {
        withContext(io) {
            dbDataSource.saveUser(user)
        }
    }

    override suspend fun getUser(): Flow<User?> {
        return flow {
            emit(
                dbDataSource.getUser()
            )
        }.flowOn(io)
    }

    override suspend fun deleteUser() {
        dbDataSource.deleteUser()
    }

    override suspend fun registerFavouriteCategory(body: CategoryListRequest): Flow<UiState<FavouriteCategoryDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.registerCategory(body)
                }
            )
        }.flowOn(io)
    }

    override suspend fun userProfileInfo(
        authorization: String,
        request: UserProfileRequest
    ): Flow<UiState<UserProfileResponse>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.postRequestUserProfile(authorization, request)
                }
            )
        }
    }
}