package com.galaxy_techno.buyer.model.dto

import java.io.Serializable

data class ItemDTO(
    val success: Boolean,
    val message: String,
    val data: List<App>
): Serializable{

data class App(
    val category: String,
    val apps: List<Apps>

) : Serializable
}



