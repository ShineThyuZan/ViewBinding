package com.galaxy_techno.buyer.model.dto


data class TownshipDTO(
    val status: String,
    val messageCode: Int,
    val message: String,
    val data: TownshipList
)

data class TownshipList(
    val townshipList: List<TownshipData>
)

data class TownshipData(
    val townshipId: Int,
    val stateId: Int,
    val township: String,
    val isDelete: Boolean
)

