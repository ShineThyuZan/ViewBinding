package com.galaxy_techno.buyer.data.remote

import com.galaxy_techno.buyer.model.dto.*
import com.galaxy_techno.buyer.model.req.CategoryListRequest
import com.galaxy_techno.buyer.model.req.UserProfileRequest
import retrofit2.Response

interface ApiDataSource {
    suspend fun requestOTP(
        mobile: String
    ): Response<OTPRequestDTO>

    suspend fun validateOTP(
        mobile: String,
        otpCode: String
    ): Response<OTPValidateDTO>

    suspend fun register(
        name: String,
        email: String,
        gender: String,
        mobile: String,
        password: String
    ): Response<RegisterDTO>

    suspend fun login(
        mobile: String,
        password: String
    ): Response<LoginDTO>

    suspend fun getFavouriteList(
    ): Response<FavouriteCategoryDTO>

    suspend fun requestNewTokenKey(
        token: String
    ): Response<TokenDTO>

    suspend fun registerCategory(
        body: CategoryListRequest
    ): Response<FavouriteCategoryDTO>

    suspend fun getCountryList(
    ): Response<CountryListResponse>

    suspend fun getStateList(
    ): Response<StateListDTO>

    suspend fun getTownshipList(
    ): Response<TownshipDTO>


    suspend fun getMovieList(
        key : String ,
        page : Int
    ): Response<ResponseMovies>

    suspend fun getSearchUser(
        user : String ,
        page : Int ,
        count : Int ,
        keyword : String
    ): Response<SearchProductResponse>

    suspend fun getMainCategoryItem(
    ): Response<ItemDTO>

    suspend fun getSelectedProduct(
    ): Response<SelectedProductDTO>


    /** userData requesting */
    suspend fun postRequestUserProfile(
        authorization : String ,
        request : UserProfileRequest
    ):Response<UserProfileResponse>
}