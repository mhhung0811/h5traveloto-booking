package com.example.h5traveloto_booking.main.presentation.schedule.components

import android.net.Uri
import android.util.Log
import androidx.collection.mutableIntSetOf
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.h5traveloto_booking.main.presentation.data.dto.Booking.BookingDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.Booking.BookingDayState
import com.example.h5traveloto_booking.main.presentation.data.dto.Booking.UserBookingDTO
import com.example.h5traveloto_booking.navigate.Screens
import com.example.h5traveloto_booking.theme.Grey50Color
import com.example.h5traveloto_booking.theme.PrimaryColor
import com.example.h5traveloto_booking.theme.SecondaryColor
import com.example.h5traveloto_booking.util.localdate_range.LocalDateProgression
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.animation.CalendarAnimator
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.config.CalendarConfig
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.config.DayState
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.config.SelectionMode
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.config.rememberCalendarState
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.modifiers.RangeEndDay
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.modifiers.RangeFullDay
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.modifiers.RangeMidDay
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.modifiers.RangeStartDate
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.utils.toMonthYear
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.utils.today
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.view.CalendarDay
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.view.CalendarView
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.view.HorizontalCalendarView
import com.example.h5traveloto_booking.util.FromStringtoDate
import com.google.gson.Gson
//import io.wojciechosak.calendar.animation.CalendarAnimator
//import io.wojciechosak.calendar.config.CalendarConfig
//import io.wojciechosak.calendar.config.DayState
//import io.wojciechosak.calendar.config.SelectionMode
//import io.wojciechosak.calendar.config.rememberCalendarState
//import io.wojciechosak.calendar.utils.toMonthYear
//import io.wojciechosak.calendar.utils.today
//import io.wojciechosak.calendar.view.CalendarView
//import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.coroutines.launch
import kotlinx.datetime.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun BookingCalendar (
    bookingList: List<UserBookingDTO>,
    navController: NavController,
) {
    val startDate by remember { mutableStateOf(LocalDate.today()) }
    val selectedDates = remember { mutableStateListOf<LocalDate>() }
    val mapBookingDayState = remember { mutableStateMapOf<LocalDate, BookingDayState>() }

    val calendarAnimator = CalendarAnimator(startDate)
    val coroutineScope = rememberCoroutineScope()

    val bookingsInDate = remember {
        mutableStateListOf<UserBookingDTO>()
    }
    val isShowBottomSheet = remember {
        mutableStateOf(false)
    }

    val rangeColor = SecondaryColor
    val rangeStrokeWidth = 2

    for (booking in bookingList) {
        // Note start date
        if (mapBookingDayState[FromStringtoDate(booking.startDate)] == null)
        {
            mapBookingDayState[FromStringtoDate(booking.startDate)] = BookingDayState(
                date = FromStringtoDate(booking.startDate),
                is_StartDate = false,
                is_MiddleDate = false,
                is_EndDate = false,
                is_FullDate = false
            )
        }
        mapBookingDayState[FromStringtoDate(booking.startDate)]?.booking_id?.add(booking.id)
        mapBookingDayState[FromStringtoDate(booking.startDate)]?.is_StartDate = true

        // Note middle date
        for (
        date: LocalDate in
        FromStringtoDate(booking.startDate).plus(DatePeriod(days = 1))..
                FromStringtoDate(booking.endDate).minus(DatePeriod(days = 1))
        ) {
            if (mapBookingDayState[date] == null)
            {
                mapBookingDayState[date] = BookingDayState(
                    date = date,
                    is_StartDate = false,
                    is_MiddleDate = false,
                    is_EndDate = false,
                    is_FullDate = false
                )
            }
            mapBookingDayState[date]?.booking_id?.add(booking.id)
            mapBookingDayState[date]?.is_MiddleDate = true
        }

        // Note end date
        if (mapBookingDayState[FromStringtoDate(booking.endDate)] == null)
        {
            mapBookingDayState[FromStringtoDate(booking.endDate)] = BookingDayState(
                date = FromStringtoDate(booking.endDate),
                is_StartDate = false,
                is_MiddleDate = false,
                is_EndDate = false,
                is_FullDate = false
            )
        }
        mapBookingDayState[FromStringtoDate(booking.endDate)]?.booking_id?.add(booking.id)
        mapBookingDayState[FromStringtoDate(booking.endDate)]?.is_EndDate = true;

        // Note full date
        if (FromStringtoDate(booking.startDate) == FromStringtoDate(booking.endDate)) {
            if (mapBookingDayState[FromStringtoDate(booking.startDate)] == null)
            {
                mapBookingDayState[FromStringtoDate(booking.startDate)] = BookingDayState(
                    date = FromStringtoDate(booking.startDate),
                    is_StartDate = false,
                    is_MiddleDate = false,
                    is_EndDate = false,
                    is_FullDate = false
                )
            }
            mapBookingDayState[FromStringtoDate(booking.startDate)]?.booking_id?.add(booking.id)
            mapBookingDayState[FromStringtoDate(booking.startDate)]?.is_FullDate = true
        }
    }


    if (isShowBottomSheet.value) {
        ListBookingDialog (
            bookingList = bookingsInDate,
            navController = navController,
            onDismiss = {
                isShowBottomSheet.value = false
            }
        )
    }

    HorizontalCalendarView(
        modifier = Modifier
            .height(435.dp)
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Grey50Color),
        startDate = startDate,
        beyondBoundsPageCount = 1,
        calendarAnimator = calendarAnimator
    ) { monthOffset ->
        CalendarView(
            modifier = Modifier
                .padding(10.dp),
            day = { dayState ->
                CalendarDay(
                    state = dayState,
                    modifier = Modifier
                        .padding(0.dp, 5.dp, 0.dp, 5.dp)
                        .then(
                            if (mapBookingDayState[dayState.date] != null) {
                                Modifier
                                    .then(
                                        if (mapBookingDayState[dayState.date]!!.is_StartDate) {
                                            Modifier.RangeStartDate(rangeStrokeWidth, rangeColor)
                                        } else Modifier
                                    )
                                    .then(
                                        if (mapBookingDayState[dayState.date]!!.is_MiddleDate) {
                                            Modifier.RangeMidDay(rangeStrokeWidth, rangeColor)
                                        } else Modifier
                                    )
                                    .then(
                                        if (mapBookingDayState[dayState.date]!!.is_EndDate) {
                                            Modifier.RangeEndDay(rangeStrokeWidth, rangeColor)
                                        } else Modifier
                                    )
                                    .then(
                                        if (mapBookingDayState[dayState.date]!!.is_FullDate) {
                                            Modifier.RangeFullDay(rangeStrokeWidth, rangeColor)
                                        } else Modifier
                                    )
                            } else {
                                Modifier
                            }
                        )
                )
            },
            header = { month, year ->
                MonthHeader(
                    month = month,
                    year = year,
                    onLeftClick = {
                        coroutineScope.launch {
                            calendarAnimator.animateTo(
                                target = LocalDate(
                                    startDate.year,
                                    startDate.month.plus(monthOffset.toLong()),
                                    startDate.dayOfMonth
                                ).plus(-1, DateTimeUnit.MONTH),
                            )
                        }
                    },
                    onRightClick = {
                        coroutineScope.launch {
                            calendarAnimator.animateTo(
                                target = LocalDate(
                                    startDate.year,
                                    startDate.month.plus(monthOffset.toLong()),
                                    startDate.dayOfMonth
                                ).plus(1, DateTimeUnit.MONTH)
                            )
                        }
                    },
                    onComplete = { monthSelected, yearSelected ->
                        coroutineScope.launch {
                            calendarAnimator.animateTo(
                                target = LocalDate(
                                    yearSelected,
                                    monthSelected,
                                    startDate.dayOfMonth
                                )
                            )
                        }
                    }
                )
            },
            config = rememberCalendarState(
                startDate = startDate,
                monthOffset = monthOffset,
                selectedDates = selectedDates
            ),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
//            selectionMode = SelectionMode.Range,
            onDateSelected = {
                bookingsInDate.clear()
                if (mapBookingDayState[it[0]] != null) {
                    for (bookingId in mapBookingDayState[it[0]]!!.booking_id) {
                        bookingList.find {
                            it.id == bookingId
                        }?.let { it1 -> bookingsInDate.add(it1) }
                    }
                    if (bookingsInDate.size > 1) {
                        isShowBottomSheet.value = true
                    }
                    else {
                        navController.navigate("${Screens.BookingDetailsScreen.name}/${Uri.encode(Gson().toJson(bookingsInDate.first()))}")
                    }
                    Log.d("Booking Calendar", bookingsInDate.size.toString())
                    Log.d("Booking Calendar", mapBookingDayState[it[0]]!!.booking_id.size.toString())
                    Log.d("Booking Calendar", bookingsInDate.toString())
                }
            },
//            rangeConfig =
//                RangeConfig(
//                    color = Color.Red,
//                    rangeIllustrator = RoundedRangeIllustrator(Color.Red)
//                )
        )
    }
}

// LocalDate CloseRange Operator
operator fun LocalDate.rangeTo(other: LocalDate) = LocalDateProgression(this, other)