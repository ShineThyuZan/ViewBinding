package com.galaxy_techno.buyer.model.dto

import java.io.Serializable

data class DetailItemDTO(
    val success: Boolean,
    val message: String,
    val data: List<App>
): Serializable{

    class App(
        val category: String,
        val apps: List<Info>

    ) : Serializable
}



