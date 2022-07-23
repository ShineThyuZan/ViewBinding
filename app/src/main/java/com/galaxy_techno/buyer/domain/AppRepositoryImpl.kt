package com.galaxy_techno.buyer.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.galaxy_techno.buyer.common.BaseDataSource
import com.galaxy_techno.buyer.common.UiState
import com.galaxy_techno.buyer.data.db.DbDataSource
import com.galaxy_techno.buyer.data.ds.DsDataSource
import com.galaxy_techno.buyer.data.remote.ApiDataSource
import com.galaxy_techno.buyer.data.remote.ApiService
import com.galaxy_techno.buyer.di.Qualifier
import com.galaxy_techno.buyer.model.dto.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val dbDataSource: DbDataSource,
    private val dsDataSource: DsDataSource,
    private val apiService: ApiService,
    @Qualifier.Io private val io: CoroutineDispatcher
) : AppRepository, BaseDataSource() {

    override suspend fun storeAuthState(isLoggedIn: Boolean) {
        withContext(io) {
            dsDataSource.saveAuthState(isLoggedIn)
        }
    }

    override suspend fun getAuthState(): Flow<Boolean> {
        return dsDataSource.getAuthState()
    }


    override suspend fun fetchToken(): Flow<UiState<TokenDTO>> {
        return flow{
            emit(
                safeApiCall {
                    apiDataSource.requestNewTokenKey("")
                }
            )
        }.flowOn(io)
    }

    override suspend fun getFavouriteList(): Flow<UiState<FavouriteCategoryDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getFavouriteList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getCountryList(): Flow<UiState<CountryListResponse>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getCountryList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getStateList(): Flow<UiState<StateListDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getStateList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun getTownshipList(): Flow<UiState<TownshipDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getTownshipList()
                }
            )
        }.flowOn(io)
    }

    override suspend fun setToken(token: String) {
        withContext(io){
            dsDataSource.saveToken(token)
        }
    }

    override suspend fun getToken(): Flow<String> {
        return dsDataSource.getToken()
    }


    /** category and promotion products in MainScreen */
    override suspend fun getMainCategoryItem(): Flow<UiState<ItemDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getMainCategoryItem()
                }
            )
        }.flowOn(io)
    }



    override suspend fun getSearchUser(user: String, page: Int, count : Int, keyword : String ) : Flow<UiState<SearchProductResponse>>{
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getSearchUser(
                        user, page , count, keyword
                    )
                }
            )
        }.flowOn(io)
    }


    override suspend fun getMovies(key : String , page : Int ): Flow<UiState<ResponseMovies>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getMovieList(key,page)
                }
            )
        }.flowOn(io)
    }

    override suspend fun getPagingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                maxSize = 25 + (25 * 2),
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagerDataSource(apiService) }
        ).flow
    }
    /** category and promotion products in MainScreen */
    override suspend fun getSelectedProduct(): Flow<UiState<SelectedProductDTO>> {
        return flow {
            emit(
                safeApiCall {
                    apiDataSource.getSelectedProduct()
                }
            )
        }.flowOn(io)

    }


}