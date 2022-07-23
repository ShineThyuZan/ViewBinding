package com.galaxy_techno.buyer.data.remote

import com.galaxy_techno.buyer.model.dto.*
import com.galaxy_techno.buyer.model.req.CategoryListRequest
import com.galaxy_techno.buyer.model.req.UserProfileRequest
import retrofit2.Response
import javax.inject.Inject

open class ApiDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : ApiDataSource {
    override suspend fun requestOTP(
        mobile: String
    ): Response<OTPRequestDTO> {
        return apiService.requestOTP(
            mobile
        )
    }

    override suspend fun validateOTP(
        mobile: String,
        otpCode: String
    ): Response<OTPValidateDTO> {
        return apiService.validateOTP(
            mobile,
            otpCode
        )
    }

    override suspend fun register(
        name: String,
        email: String,
        gender: String,
        mobile: String,
        password: String
    ): Response<RegisterDTO> {
        return apiService.register(
            name,
            email,
            gender,
            mobile,
            password
        )
    }
    override suspend fun login(
        mobile: String,
        password: String
    ): Response<LoginDTO> {
        return apiService.login(
            mobile,
            password
        )
    }
    override suspend fun getFavouriteList(
    ): Response<FavouriteCategoryDTO> {
        return apiService.favouriteList()
    }

    override suspend fun registerCategory(
        body: CategoryListRequest
    ): Response<FavouriteCategoryDTO> {
        return apiService.registerFavouriteCategory(body)
    }

    override suspend fun getCountryList(): Response<CountryListResponse> {
        return apiService.getCountryList()
    }

    override suspend fun getStateList(): Response<StateListDTO> {
        return apiService.getStateList()
    }

    override suspend fun getTownshipList(): Response<TownshipDTO> {
        return apiService.getTownshipList()
    }

    override suspend fun requestNewTokenKey(
        token : String
    ): Response<TokenDTO> {
        return apiService.getRefreshToken()
    }

    override suspend fun getMainCategoryItem(): Response<ItemDTO> {
        return apiService.getMainCategoryItem()
    }

    override suspend fun getMovieList(key: String, page: Int): Response<ResponseMovies> {
        return apiService.fetchMovies(
            key,
            page
        )
    }

    /** user profile data calling */
    override suspend fun postRequestUserProfile(
        authorization: String,
        request: UserProfileRequest
    ): Response<UserProfileResponse> {
      return apiService.userProfileData(
          authorization,
          request
      )
    }

    override suspend fun getSelectedProduct(): Response<SelectedProductDTO> {
        return apiService.getSelectedProduct()
    }

    override suspend fun getSearchUser(user : String , page : Int , count : Int , keyword: String ): Response<SearchProductResponse>{
        return apiService.searchUser(
            user, page, count,keyword
        )
    }



}