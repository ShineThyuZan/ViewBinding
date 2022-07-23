package com.galaxy_techno.buyer.model.dto

data class CountryDTO(
    val data: CountryList,
    val message: String,
    val messageCode: Int,
    val status: String
)
data class CountryList(
    val countryList: List<Country>
)
data class Country(
    val country: String,
    val countryCode: String,
    val countryId: Int,
    val isDelete: Boolean
)