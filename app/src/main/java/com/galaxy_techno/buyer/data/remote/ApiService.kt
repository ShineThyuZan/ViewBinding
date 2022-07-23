package com.galaxy_techno.buyer.data.remote

import com.galaxy_techno.buyer.common.Endpoint
import com.galaxy_techno.buyer.model.dto.*
import com.galaxy_techno.buyer.model.req.CategoryListRequest
import com.galaxy_techno.buyer.model.req.UserProfileRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST(Endpoint.GET_OTP)
    suspend fun requestOTP(
        @Field("mobile") mobileNumber: String
    ): Response<OTPRequestDTO>

    @FormUrlEncoded
    @POST(Endpoint.VALIDATE_OTP)
    suspend fun validateOTP(
        @Field("mobile") mobileNumber: String,
        @Field("otpCode") otpCode: String
    ): Response<OTPValidateDTO>

    @FormUrlEncoded
    @POST(Endpoint.REGISTER)
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("gender") gender: String,
        @Field("mobile") mobile: String,
        @Field("password") password: String
    ): Response<RegisterDTO>

    @FormUrlEncoded
    @POST(Endpoint.LOGIN)
    suspend fun login(
        @Field("mobile") mobileNumber: String,
        @Field("password") token: String
    ): Response<LoginDTO>

    @GET(Endpoint.GET_CATEGORY_LIST)
    suspend fun favouriteList(
//        @Header(Endpoint.AUTHORIZATION) token: String
    ): Response<FavouriteCategoryDTO>

    @POST(Endpoint.REGISTER_FAVOURITE_CATEGORY)
    suspend fun registerFavouriteCategory(
        @Body body: CategoryListRequest
    ): Response<FavouriteCategoryDTO>

    @GET("https://iix1ylnorg.execute-api.ap-southeast-1.amazonaws.com/test/language/list")
    suspend fun getCountryList(): Response<CountryListResponse>

    @GET(Endpoint.GET_STATE_LIST)
    suspend fun getStateList(): Response<StateListDTO>

    @GET(Endpoint.GET_TOWNSHIP_LIST)
    suspend fun getTownshipList(): Response<TownshipDTO>

    @GET(Endpoint.GET_REFRESH_TOKEN)
    suspend fun getRefreshToken(): Response<TokenDTO>

    @GET("https://iix1ylnorg.execute-api.ap-southeast-1.amazonaws.com/test/apps")
    suspend fun getMainCategoryItem(): Response<ItemDTO>

    @GET("https://api.themoviedb.org/3/movie/popular")
    suspend fun fetchMovies(
        @Query("api_key") key: String,
        @Query("page") pageNumber: Int
    ): Response<ResponseMovies>

    @GET("https://iix1ylnorg.execute-api.ap-southeast-1.amazonaws.com/test/feed/searching/users/userid/{userid}/page/{pageno}/count/{countno}/search/")
   suspend fun searchUser(
        @Path("userid") userId: String,
        @Path("pageno") page: Int,
        @Path("countno") count: Int,
        @Query("keyword") keyword : String
    ): Response<SearchProductResponse>

    @GET("https://iix1ylnorg.execute-api.ap-southeast-1.amazonaws.com/test/foundation/bank/list")
    suspend fun getSelectedProduct(): Response<SelectedProductDTO>

    /** take User profile information in TopLev profile */
    @POST("https://j02r2116m5.execute-api.ap-southeast-1.amazonaws.com/prod/userprofile/userprofile")
    suspend fun userProfileData(
        @Header("Authorization") token: String,
        @Body request: UserProfileRequest
    ): Response<UserProfileResponse>



}