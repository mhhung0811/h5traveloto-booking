package com.example.h5traveloto_booking.main.presentation.home

import android.app.Activity
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.h5traveloto_booking.R
import com.example.h5traveloto_booking.main.presentation.data.dto.Search.District
import com.example.h5traveloto_booking.main.presentation.home.components.HotelTagLarge
import com.example.h5traveloto_booking.main.presentation.home.components.HotelTagSmall
import com.example.h5traveloto_booking.main.presentation.map.LocationProvider
import com.example.h5traveloto_booking.navigate.Screens
import com.example.h5traveloto_booking.share.ShareHotelDataViewModel
import com.example.h5traveloto_booking.share.shareDataHotelDetail
import com.example.h5traveloto_booking.share.shareHotelDataViewModel
import com.example.h5traveloto_booking.theme.*
import com.example.h5traveloto_booking.ui_shared_components.*
import com.example.h5traveloto_booking.util.ui_shared_components.PrimaryButton
import com.example.h5traveloto_booking.util.Result
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    navAppController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val launchMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        Log.d("HomeScreen", "")
        if (permissions.all { it.value }) {
            if(LocationProvider.isLocationEnabled(context)){
                viewModel.startLocationUpdates()
            }
            else{
                Log.d("HomeScreen", "GPS not enabled ne ne")
                LocationProvider.createLocationRequest(context)
            }
        } else {
            Log.d("LocationProvider", "Permissions denied")
        }
    }

    val enableGpsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.startLocationUpdates()
        } else {
            Log.d("LocationProvider", "GPS enabling denied")
        }
    }

    val listHotelSearch = viewModel.ListHotelSearch.collectAsState().value
    val listProminentHotel = viewModel.ListProminentHotel.collectAsState().value
    val selectedItemIndex = rememberSaveable { mutableStateOf(0) }

    //
    LaunchedEffect(UInt) {
        LocationProvider.setGpsLauncher(enableGpsLauncher)
        shareHotelDataViewModel.setIsCurrentLocation(true)
        viewModel.getListDistricts()
        viewModel.getProminentHotel()
        viewModel.initLocationProvider(context)
        Log.d("HomeScreen", "LaunchedEffect")
    }

    val listDistrict = viewModel.listDistrict.collectAsState().value
    Spacer(modifier = Modifier.height(10.dp))

    Scaffold(
        modifier = Modifier.background(ScreenBackGround),
        topBar = {
            Row(
                Modifier
                    .padding(24.dp, 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    /*Text(text = "Current location")
                    Spacer(modifier = Modifier.height(4.dp))*/
                    MenuDown(items =
                        if (listDistrict is Result.Success) {
                            listDistrict.data.districts
                        } else {
                            listOf(District("Vị trí gần tôi", ""))
                        },
                        selectedItemIndex = selectedItemIndex.value,
                        onItemSelected = { index, province ->
                            selectedItemIndex.value = index
                            if(index == 0) {
                                shareHotelDataViewModel.setIsCurrentLocation(true)
                                shareHotelDataViewModel.setSearchTerm("location")
                                viewModel.setStateHotelSearchIdle()
                                shareHotelDataViewModel.logHotelParams()
                            }
                            else{
                                shareHotelDataViewModel.setIsCurrentLocation(false)
                                shareHotelDataViewModel.setId(province.code)
                                shareHotelDataViewModel.setSearchTerm("province")
                                viewModel.setStateHotelSearchLoading()
                                viewModel.getHotelSearch()
                                shareHotelDataViewModel.logHotelParams()
                            }
                        })
                }
                Column {
                    PrimaryIconButton(DrawableId = R.drawable.message_circle, onClick = {
                        navAppController.navigate(Screens.AllChatScreen.name)
                    }, alt = "")
                }
            }
        }


    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Button(
                        onClick = { navController.navigate(Screens.HomeSearchScreen.name) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 0.dp)
                            .border(2.dp, BorderStroke, RoundedCornerShape(8.dp))
                            .height(52.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        ),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(id = R.drawable.search),
                                contentDescription = "search",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Tìm kiếm khách sạn",
                                fontSize = 14.sp,
                                color = Grey500Color,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp, 0.dp)
                            )
                            Image(
                                painterResource(id = R.drawable.searchfilter),
                                contentDescription = "search",
                                modifier = Modifier.size(28.dp)
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            text = "Vị trí gần bạn"
                        )
                        Text(
                            text = "Xem tất cả",
                            fontSize = 16.sp,
                            color = if(viewModel.checkData()) PrimaryColor else Grey500Color,
                            modifier = Modifier.clickable {
                                if(viewModel.checkData()){
                                    shareHotelDataViewModel.setListHotel((listHotelSearch as Result.Success).data)
                                    shareHotelDataViewModel.setOnClickBooking(false)
                                    navAppController.navigate(Screens.ListHotels.name)
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                when(listHotelSearch){
                    is Result.Idle -> {
                        if(shareHotelDataViewModel.isCurrentLocation()){
                            if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED
                                || ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED
                                || !LocationProvider.isLocationEnabled(context)
                            ){
                                ButtonRequestLocationPermission(onClick = {
                                    launchMultiplePermissions.launch(permissions)
                                })
                            }
                            else{
                                viewModel.initLocationProvider(context)
                                launchMultiplePermissions.launch(permissions)
                            }
                        }
                        else{
                            if(shareHotelDataViewModel.getSearchHotelParams().id != ""){
                                viewModel.getHotelSearch()
                            }
                        }
                    }
                    is Result.Loading -> {
                        Box( contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                           CircularProgressIndicator()
                        }
                    }
                    is Result.Error -> {
                        NotFoundHotel()
                    }
                    is Result.Success -> {
                        val hotels = listHotelSearch.data.data
                        LazyRow(modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)) {
                            if(hotels!= null){
                                hotels.forEachIndexed { index, hotelDTO ->
                                    item {
                                        HotelTagLarge(hotelDTO, onClick = {
                                            viewModel.postClickHotel(hotelDTO.id)
                                            shareDataHotelDetail.setHotelDetails(hotelDTO)
                                            shareDataHotelDetail.setHotelId(hotelDTO.id)
                                            shareDataHotelDetail.LogData()
                                            navAppController.navigate(Screens.HotelDetailsScreen.name)
                                        })
                                    }
                                    if(index >= 3){
                                        return@LazyRow
                                    }
                                }
                            }
                            else{
                                viewModel.setStateHotelSearchError()
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(22.dp))
                Column(modifier = Modifier.padding(16.dp, 0.dp)) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BoldText("Địa điểm nổi bật")
                        Text(
                            text = "Xem tất cả",
                            fontSize = 16.sp,
                            color = if(viewModel.checkDataProminent()) PrimaryColor else Grey500Color,
                            modifier = Modifier.clickable {
                                if(viewModel.checkData()){
                                    shareHotelDataViewModel.setIsCurrentLocation(false)
                                    shareHotelDataViewModel.setListHotel((listProminentHotel as Result.Success).data)
                                    shareHotelDataViewModel.setOnClickBooking(false)
                                    navAppController.navigate(Screens.ListHotels.name)
                                }
                            }
                        )
                    }
                    when(listProminentHotel){
                        is Result.Idle -> {
                            viewModel.setStateProminentHotelLoading()
                        }
                        is Result.Loading -> {
                            Box( contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator()
                            }
                        }
                        is Result.Error -> {
                            NotFoundHotel()
                        }
                        is Result.Success -> {
                            val hotels = listProminentHotel.data.data
                            Column(modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp)) {
                                if(hotels!= null){
                                    hotels.forEachIndexed { index, hotelDTO ->
                                            HotelTagSmall(hotelDTO, onClick = {
                                                viewModel.postClickHotel(hotelDTO.id)
                                                shareDataHotelDetail.setHotelDetails(hotelDTO)
                                                shareDataHotelDetail.setHotelId(hotelDTO.id)
                                                shareDataHotelDetail.LogData()
                                                navAppController.navigate(Screens.HotelDetailsScreen.name)
                                            })
                                            YSpacer(12)
                                        if (index >= 3) {
                                            return@Column
                                        }
                                    }
                                }
                                else{
                                    viewModel.setStateProminentHotelError()
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}