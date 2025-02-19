package com.example.h5traveloto_booking.details.presentation.hoteldetails

import ExpandingText
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.h5traveloto_booking.R
import com.example.h5traveloto_booking.account.ListHotelsViewModel
import com.example.h5traveloto_booking.details.presentation.hoteldetails.components.*
import com.example.h5traveloto_booking.details.presentation.roomdetails.components.RoomDetailCard
import com.example.h5traveloto_booking.details.presentation.roomdetails.components.RoomFacilitiesList
import com.example.h5traveloto_booking.main.presentation.favorite.AllFavorite.formatPrice
import com.example.h5traveloto_booking.main.presentation.favorite.DetailCollection.DetailCollectionViewModel
import com.example.h5traveloto_booking.main.presentation.favorite.FavoriteViewModel

import com.example.h5traveloto_booking.navigate.Screens
import com.example.h5traveloto_booking.share.shareDataHotelDetail
import com.example.h5traveloto_booking.theme.Grey500Color
import com.example.h5traveloto_booking.theme.PrimaryColor
import com.example.h5traveloto_booking.ui_shared_components.*
import com.example.h5traveloto_booking.ui_shared_components.my_calendar.modifiers.RangeMidDay
import com.example.h5traveloto_booking.util.Result
import com.example.h5traveloto_booking.util.ui_shared_components.PrimaryButton
import com.google.gson.Gson

@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelDetailsScreen(
    navController: NavController,
    viewModel: HotelDetailsScreenViewModel = hiltViewModel(),
    hotelFacilitiesDetailsViewModel: HotelFacilitiesDetailsViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    unsaveViewModel: DetailCollectionViewModel = hiltViewModel(),

    ) {
    LaunchedEffect(Unit) {
        hotelFacilitiesDetailsViewModel.getHotelFacilitiesDetails()
        viewModel.getHotelDetails()
        viewModel.getListReviews()

    }
    val  listHotelFacilitiesDetails = hotelFacilitiesDetailsViewModel.hotelacilitiesDetailsResponse.collectAsState().value
    val HotelDetailsResponse = viewModel.HotelDetailsResponse.collectAsState().value

    val hotelInfo = shareDataHotelDetail.getHotelDetails();
    val Response = favoriteViewModel.IsSavedResponse.collectAsState().value
    var favoriteState by remember { mutableStateOf(false) }

    LaunchedEffect(HotelDetailsResponse){
        favoriteViewModel.isSaved(hotelInfo?.id.toString())
        when(Response){
            is Result.Success ->{
                favoriteState = Response.data.data
            }
            else ->Unit
        }
    }
    val listReviewsResponse = viewModel.ListReviewsResponse.collectAsState().value
    var tengicungduoc: com.example.h5traveloto_booking.details.presentation.data.dto.hotelDetails.HotelDetailsDTO? =
        null
    val imageUrlList = hotelInfo!!.images?.map { it.url }
    var currentImageUrl by remember {
        mutableStateOf(imageUrlList?.get(0) ?:"" )
    }
    var isLogoOpen by remember {
        mutableStateOf(false)
    }
    var isImageOpen by remember {
        mutableStateOf(false)
    }
    when (isLogoOpen) {
        true -> hotelInfo.logo?.let { DialogWithImage(onDismissRequest = { isLogoOpen = false }, imageURL = it.url) }
        false -> {}
    }
    when (isImageOpen) {
        true -> DialogWithImage(onDismissRequest = { isImageOpen = false }, imageURL = currentImageUrl)
        false -> {}
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    GreyText(text = "Giá/phòng/đêm từ")
                    PrimaryText20(
                        text = "${hotelInfo!!.displayPrice?.formatPrice()} VND",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                    BoldText(text = "Giá cuối cùng")
                }

                PrimaryButton(
                    onClick = {
                        navController.navigate(Screens.ListRooms.name);
                        shareDataHotelDetail.setHotelName(hotelInfo.name)
                    },
                    text = "Chọn Phòng",
                    modifier = Modifier
                        .width(150.dp)
                        .height(65.dp)
                )
            }

        },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrimaryIconButton(
                    DrawableId = R.drawable.backbutton, onClick = { navController.popBackStack() }, alt = ""
                )
                BoldText(text = "Chi tiết khách sạn")
                PrimaryIconButton(
                    DrawableId = R.drawable.message_circle,
                    onClick = {
                        navController.navigate(Screens.ChatScreen.name);
                        shareDataHotelDetail.setHotelName(hotelInfo.name)
                    },
                    alt = ""
                )
            }
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (HotelDetailsResponse) {

                is Result.Error -> {
                    Log.d("HotelDetails ", "loi roi")
                }

                is Result.Loading -> {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Success -> {
                    tengicungduoc = HotelDetailsResponse.data

                    shareDataHotelDetail.setCheckInTime(HotelDetailsResponse.data.data.checkInTime)
                    shareDataHotelDetail.setCheckOutTime(HotelDetailsResponse.data.data.checkOutTime)


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                    ) {
                        item {
                            Box(
                                contentAlignment = Alignment.TopEnd,
                            ) {
                                AsyncImage(
                                    model = hotelInfo!!.logo?.url,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(246.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { isLogoOpen = true },
                                    contentScale = ContentScale.Crop,
                                )
                                IconButton(modifier = Modifier
                                    .padding(8.dp)
                                    .offset(x = (-8).dp, y = 8.dp)
                                    .width(32.dp)
                                    .height(32.dp)
                                    .background(
                                        color = Color.White, shape = RoundedCornerShape(50.dp)
                                    ), onClick = {
                                        if(favoriteState){
                                            unsaveViewModel.unsaveHotel(hotelInfo.id)
                                        }else {
                                            favoriteViewModel.save(hotelId = hotelInfo.id)
                                        }
                                        favoriteState = !favoriteState }, content = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(
                                            id = if (favoriteState) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                                        ),
                                        contentDescription = "Favorite Icon",
                                        tint = if (favoriteState) Color.Red else Color.Gray
                                    )
                                })
                            }

                            YSpacer(height = 16)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                BoldText(text = hotelInfo!!.name)
                                HotelServiceTag(
                                    DrawableId = R.drawable.star,
                                    alt = "rating",
                                    text = "${hotelInfo.star}.0",
                                    iconColor = Color(0xffffe234),
                                )
                            }
                            YSpacer(height = 8)
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.location),
                                    contentDescription = "Location",
                                    contentScale = ContentScale.Crop
                                )
                                XSpacer(width = 10)
                                GreyText(text = "${hotelInfo!!.district.fullName}, ${hotelInfo.province?.fullName}")
                            }
                            YSpacer(height = 16)
                            BoldText(text = "Mô Tả Khách Sạn")
                            YSpacer(height = 12)
                            ExpandingText(
                                longText = HotelDetailsResponse.data.data.description

                            )
                            YSpacer(height = 16)
                            HorizontalDivider(color = Color.Black, thickness = 0.1.dp)
                            YSpacer(height = 8)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                BoldText(text = "Đánh Giá")
                                PrimaryText(
                                    text = "Xem tất cả",
                                    modifier = Modifier.clickable { navController.navigate(Screens.ListReviews.name) })
                            }
                            YSpacer(height = 12)
                            /*HotelDetailFeedback(
                                text = "The Aston Vill Hotel is a 5-star hotel located in the heart of the city. The hotel is a 5-minute walk from the city center and a 10-minute walk from the beach. The hotel offers a variety of amenities, including a spa, fitness center, and swimming pool. The hotel also has a restaurant and bar, where guests can enjoy a variety of dishes and drinks.",
                                author = "John Doe"
                            )*/
                            YSpacer(height = 16)
                            HorizontalDivider(color = Color.Black, thickness = 0.1.dp)
                            YSpacer(height = 8)
                            BoldText(text = "Hình Ảnh Xem Trước")
                            YSpacer(height = 8)
                            LazyRow(modifier = Modifier.fillMaxWidth()) {
                                item {
                                    if (imageUrlList != null) {
                                        imageUrlList.forEachIndexed { index, imageDTO ->
                                            AsyncImage(
                                                model = imageDTO,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .width(98.dp)
                                                    .height(82.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable { currentImageUrl = imageDTO; isImageOpen = true },
                                                contentScale = ContentScale.Crop,
                                            )
                                            XSpacer(width = 16)

                                        }
                                    }
                                }

                            }
                            YSpacer(height = 16)
                            HorizontalDivider(color = Color.Black, thickness = 0.1.dp)
                            YSpacer(height = 8)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                BoldText(text = "Chính Sách Lưu Trú")
                                PrimaryText(
                                    text = "Xem tất cả",
                                    modifier = Modifier.clickable {
                                        navController.navigate(
                                            "${Screens.ListPolicies.name}/${
                                                Gson().toJson(
                                                    tengicungduoc
                                                )
                                            }"
                                        )
                                    })
                            }
                            YSpacer(height = 8)
                            HotelDetailPolicyCard(
                                icon = R.drawable.baseline_access_time_24,
                                text = "Giờ nhận phòng/trả phòng",
                                description = "Nhận phòng: ${HotelDetailsResponse.data.data.checkInTime} - Trả phòng: ${HotelDetailsResponse.data.data.checkOutTime}"
                            )
                            YSpacer(height = 16)
                            HorizontalDivider(color = Color.Black, thickness = 0.1.dp)
                            YSpacer(height = 8)

                            when(listHotelFacilitiesDetails){
                                is Result.Success -> {
                                    BoldText(text = "Tiện ích Khách Sạn")
                                    RoomFacilitiesList(
                                        items = listHotelFacilitiesDetails.data.data.map { data ->
                                            data.nameVn
                                        },
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Normal,
                                        ),
                                        lineSpacing = 8.dp,
                                    )
                                }
                                else -> {}
                            }



                        }
                    }

                }

                else -> Unit
            }

        }
    }
}