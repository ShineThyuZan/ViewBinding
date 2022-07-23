package com.galaxy_techno.buyer.model.req

data class CategoryListRequest(
    val customerId: Int,
    val interestedCategoryList: List<InterestedCategory>
)

data class InterestedCategory(
    val category: String,
    val categoryId: Int
)
