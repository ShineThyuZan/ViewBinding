package com.galaxy_techno.buyer.model.dto

data class SelectedProductDTO(
    val data: List<ProductData>,
    val error: Boolean,
    val message: String,
    val paging: String,
    val success: Boolean
)

data class ProductData(
    val created_datetime: String,
    val id: String,
    val name: String,
    val photo: String
)