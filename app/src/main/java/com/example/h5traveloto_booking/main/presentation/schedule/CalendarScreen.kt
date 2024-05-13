package com.example.h5traveloto_booking.main.presentation.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.h5traveloto_booking.main.presentation.data.dto.Booking.BookingDTO
import com.example.h5traveloto_booking.main.presentation.schedule.components.BookingCalendar
import com.example.h5traveloto_booking.main.presentation.schedule.components.BookingCard
import com.example.h5traveloto_booking.navigate.Screens
import com.example.h5traveloto_booking.ui_shared_components.BoldText
import com.example.h5traveloto_booking.ui_shared_components.ClickableText
import com.example.h5traveloto_booking.ui_shared_components.YSpacer
import com.example.h5traveloto_booking.ui_shared_components.DateRangePicker
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.config.CalendarConstants.MIN_DATE
import io.wojciechosak.calendar.utils.today
import kotlinx.coroutines.delay
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun CalendarScreen (
    bookingList: List<BookingDTO>,
    navController: NavController
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val list = List(3) {}

    val showDialog = remember {
        mutableStateOf(false)
    }
    val dialogVal0 = remember {
        mutableStateOf(LocalDate.today())
    }
    val dialogVal1 = remember {
        mutableStateOf(LocalDate.today().plus(1, DateTimeUnit.DAY))
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Row (
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BoldText(text = "Lịch Trình")
            }
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
        ) {
            item {
                Button(onClick = {
                    showDialog.value = true
                }) {
                    Text(
                        text = "${dialogVal0.value}   ${dialogVal1.value}",
                        color = Color.White
                    )
                }
                if (showDialog.value) {
                    DateRangePicker(
                        start = dialogVal0.value,
                        end = dialogVal1.value,
                        onCompleted = { startDate, endDate ->
                            dialogVal0.value = startDate
                            dialogVal1.value = endDate
                            showDialog.value = false
                        },
                        onDismiss = {
                            showDialog.value = false
                        }
                    )
                }

                BookingCalendar(bookingList = bookingList)
                YSpacer(height = 5)
            }
            item {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        BoldText(text = "Danh sách đặt phòng")
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ClickableText(text = "Xem thêm") {
                            navController.navigate(Screens.ScheduleBookingScreen.name)
                        }
                    }
                }
            }
            items(list) {
                BookingCard(false)
                YSpacer(height = 10)
            }
        }
    }
}