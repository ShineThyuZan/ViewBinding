package com.galaxy_techno.buyer.domain

import androidx.paging.PagingData
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.model.dto.*
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun storeAuthState(isLoggedIn: Boolean)
    suspend fun getAuthState(): Flow<Boolean>
    suspend fun getFavouriteList(): Flow<UiState<FavouriteCategoryDTO>>
    suspend fun getCountryList(): Flow<UiState<CountryListResponse>>
    suspend fun getStateList(): Flow<UiState<StateListDTO>>
    suspend fun getMainCategoryItem(): Flow<UiState<ItemDTO>>
    suspend fun getTownshipList(): Flow<UiState<TownshipDTO>>
    suspend fun fetchToken(): Flow<UiState<TokenDTO>>
    suspend fun setToken(token: String)
    suspend fun getToken(): Flow<String>
    suspend fun getMovies(key : String , page : Int ): Flow<UiState<ResponseMovies>>
    suspend fun getSearchUser(user : String, page : Int , count : Int , keyword : String ): Flow<UiState<SearchProductResponse>>
    suspend fun getPagingMovies() : Flow<PagingData<Movie>>
    suspend fun getSelectedProduct(): Flow<UiState<SelectedProductDTO>>
}