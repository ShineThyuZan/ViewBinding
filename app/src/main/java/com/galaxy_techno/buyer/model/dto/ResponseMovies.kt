package com.galaxy_techno.buyer.model.dto


/*
 "page": 1,
 "results": [ {.....},...],
 "total_pages": 500,
 "total_results": 10000
 */
data class ResponseMovies(
    val page: Int,
    val total_pages: Int,
    val total_results: Double,
    val results: List<Movie>
)