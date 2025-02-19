package com.example.h5traveloto_booking.share

import android.util.Log
import com.example.h5traveloto_booking.main.presentation.data.dto.SearchHotel.Data
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.utils.today
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import javax.inject.Singleton

class ShareDataHotelDetail {
    private var hotelId: String = ""
    private var startDate = LocalDate.today()
    private var endDate = LocalDate.today().plus(1, kotlinx.datetime.DateTimeUnit.DAY)
    private var roomQuantity: Int = 1
    private var adults: Int = 1
    private var children: Int = 0
    private var hotelDetails: Data? = null
    private var searchText :String = ""
    private var selectedStartDate: String = ""
    private var personOption: Triple<Int, Int, Int> = Triple(1, 1, 1)
    private var hotelName : String = "";
    private var roomTypeId:String="";
    private lateinit var roomDTO: com.example.h5traveloto_booking.main.presentation.data.dto.SearchRoomType.Data
    private var checkInTime: String = ""
    private var checkOutTime: String = ""



    fun setRoomDTO(roomDTO: com.example.h5traveloto_booking.main.presentation.data.dto.SearchRoomType.Data){
        this.roomDTO = roomDTO
    }
    fun getRoomDTO(): com.example.h5traveloto_booking.main.presentation.data.dto.SearchRoomType.Data{
        return roomDTO
    }

    fun setRoomTypeId(roomTypeId: String){
        this.roomTypeId = roomTypeId
    }
    fun getRoomTypeId(): String{
        return roomTypeId
    }

    fun setHotelName(hotelName: String){
        this.hotelName = hotelName
    }
    fun getHotelName(): String{
        return hotelName
    }

    fun setPersonOption(adults: Int, children: Int, roomQuantity: Int){
        personOption = Triple(adults, children, roomQuantity)
    }
    fun getPersonOption(): Triple<Int, Int, Int>{
        return personOption
    }
    fun getSelectedStartDate(): String{
        return selectedStartDate
    }
    fun setSelectedStartDate(selectedStartDate: String){
        this.selectedStartDate = selectedStartDate
    }
    fun getSearchText(): String{
        return searchText
    }
    fun setSearchText(searchText: String){
        this.searchText = searchText
    }

    fun getHotelId(): String{
        return hotelId
    }

    fun getStartDate(): LocalDate{
        return startDate
    }

    fun getEndDate(): LocalDate{
        return endDate
    }

    fun getRoomQuantity(): Int{
        return roomQuantity
    }

    fun getAdults(): Int{
        return adults
    }

    fun getChildren(): Int{
        return children
    }
    fun getHotelDetails(): Data?{
        return hotelDetails
    }
    fun setHotelDetails(hotelDetails: Data){
        this.hotelDetails = hotelDetails
    }

    fun setHotelId(hotelId: String){
        this.hotelId = hotelId
    }

    fun setStartDateEndDate(startDate: LocalDate, endDate: LocalDate){
        this.startDate = startDate
        this.endDate = endDate
    }

    fun setAdultsChildrenRoomQuantity(adults: Int, children: Int, roomQuantity: Int){
        this.adults = adults
        this.children = children
        this.roomQuantity = roomQuantity
    }

    fun LogData() {
        Log.d("ShareDataHotelDetail", "hotelId: $hotelId, startDate: $startDate, endDate: $endDate, roomQuantity: $roomQuantity, adults: $adults, children: $children")
    }

    private fun setStartDateEndDate(localDate: LocalDate): String{
        val Day = if(localDate.dayOfMonth < 10) "0${localDate.dayOfMonth}" else localDate.dayOfMonth.toString()
        val Month = if(localDate.monthNumber < 10) "0${localDate.monthNumber}" else localDate.monthNumber.toString()
        val Year = localDate.year.toString()
        return "$Day-$Month-$Year"
    }

    fun getStartDateString(): String{
        return setStartDateEndDate(startDate)
    }

    fun getEndDateString(): String{
        return setStartDateEndDate(endDate)
    }

    fun setCheckInTime(time: String) {
        checkInTime = time
    }
    fun getCheckInTime(): String {
        return checkInTime
    }

    fun setCheckOutTime(time: String) {
        checkOutTime = time
    }
    fun getCheckOutTime(): String {
        return checkOutTime
    }
}

@Singleton
val shareDataHotelDetail = ShareDataHotelDetail()