package com.galaxy_techno.buyer.model.dto

class CountryListResponse (
    val success : Boolean,
    val message : String,
    val data : List<CountryVos>,
    val paging : String,
    val error: String
)