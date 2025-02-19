package com.example.h5traveloto_booking.main.presentation.data.dto.Favorite


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "data")
    val `data`: Boolean
)


@JsonClass(generateAdapter = true)
data class CreateResponse(
    @Json(name="data")
    val collectionId : String
)