package com.galaxy_techno.buyer.model.dto

data class StateListDTO(
    val status: String,
    val messageCode: Int,

    val data: StateList? = null,
    val message: String? = null,
    val error: String? = null
)

data class StateList(
    val stateList: List<State>
)

data class State(
    val countryId: Int,
    val isDelete: Boolean,
    val state: String,
    val stateId: Int
)