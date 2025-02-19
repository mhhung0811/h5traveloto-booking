package com.example.h5traveloto_booking.main.presentation.domain.repository

import com.example.h5traveloto_booking.main.presentation.data.dto.Search.DistrictsDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.Search.SuggestionsDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.SearchHotel.SearchHotelDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.SearchHotel.SearchHotelParams
import com.example.h5traveloto_booking.main.presentation.data.dto.SearchRoomType.SearchRoomTypeDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.SearchRoomType.SearchRoomTypeParams

interface SearchRepository {
    suspend fun listDistricts() : DistrictsDTO
    suspend fun searchSuggestions(limit: Int, searchText: String) : SuggestionsDTO
    suspend fun searchHotels(params: SearchHotelParams): SearchHotelDTO
    suspend fun searchRoomTypes(params: SearchRoomTypeParams) : SearchRoomTypeDTO
    suspend fun searchProminentHotels(limit: Int) : SearchHotelDTO
    suspend fun searchViewedHotels(token: String) : SearchHotelDTO
}