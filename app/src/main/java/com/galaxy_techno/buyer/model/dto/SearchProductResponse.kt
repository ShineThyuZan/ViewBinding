package com.galaxy_techno.buyer.model.dto
class SearchProductResponse(
    val success: Boolean,
    val message: String,
    val data: List<SearchResult>,
    val error: String
) {
    data class SearchResult(

        val userId: String,
        val userName: String,
        val userPhoto: String,
        val followStatus: String,
        val followerCount: Int
    )
}