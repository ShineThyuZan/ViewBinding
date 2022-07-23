package com.galaxy_techno.buyer.model.dto

import com.galaxy_techno.buyer.model.req.InterestedCategory
import com.google.gson.annotations.SerializedName

data class FavouriteCategoryDTO(
    val status: String,
    val messageCode: Int,

    val data: CategoryList? = null,
    val message: String? = null,
    val error: String? = null
)

data class CategoryList(
    val categoryList: List<CategoryItem>
)

data class CategoryItem(
    @SerializedName("category")
    val categoryName: String,
    val categoryId: Int
)

fun CategoryItem.toBody(): InterestedCategory {
    return InterestedCategory(
        category = categoryName,
        categoryId = categoryId
    )
}

