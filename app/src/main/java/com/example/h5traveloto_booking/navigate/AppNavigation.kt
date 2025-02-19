package com.example.h5traveloto_booking.navigate

import ListRooms
import WebViewScreen3
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.h5traveloto_booking.account.AboutUsScreen.AboutUsScreen
import com.example.h5traveloto_booking.account.ChangePassword.ChangePasswordScreen
import com.example.h5traveloto_booking.auth.presentation.login.LoginScreen
import com.example.h5traveloto_booking.auth.presentation.signup.SignUpScreen
import com.example.h5traveloto_booking.main.presentation.MainScreen
import com.example.h5traveloto_booking.account.personal_information.PersonalInformationScreen
import com.example.h5traveloto_booking.account.personal_information.UpdateInformation.UpdateInformationScreen
import com.example.h5traveloto_booking.chat.presentation.AllChatScreen
import com.example.h5traveloto_booking.auth.presentation.forgotpassword.ForgotPasswordScreen
import com.example.h5traveloto_booking.chat.presentation.ChatScreen
import com.example.h5traveloto_booking.chat.presentation.components.AllChatCard
import com.example.h5traveloto_booking.details.presentation.bookingdetails.BookingDetailsScreen
import com.example.h5traveloto_booking.details.presentation.bookingdetails.BookingScreen
import com.example.h5traveloto_booking.details.presentation.bookingdetails.BookingScreenViewModel
import com.example.h5traveloto_booking.details.presentation.bookingdetails.screens.BookingDetailsFillingScreen
import com.example.h5traveloto_booking.details.presentation.bookingdetails.screens.BookingReviewScreen
import com.example.h5traveloto_booking.details.presentation.data.dto.hotelDetails.HotelDetailsDTO
import com.example.h5traveloto_booking.details.presentation.hoteldetails.HotelDetailsScreen
import com.example.h5traveloto_booking.details.presentation.hoteldetails.ListHotels
import com.example.h5traveloto_booking.details.presentation.hoteldetails.components.ListPolicies
import com.example.h5traveloto_booking.details.presentation.hoteldetails.components.ListReviews
import com.example.h5traveloto_booking.details.presentation.roomdetails.RoomDetailsScreen
import com.example.h5traveloto_booking.main.presentation.favorite.AddCollection.AddCollectionScreen
import com.example.h5traveloto_booking.main.presentation.favorite.AddHotelInCollection.AddHotelInCollectionScreen
import com.example.h5traveloto_booking.main.presentation.data.dto.Booking.BookingDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.Booking.CreateBookingDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.Favorite.Data
import com.example.h5traveloto_booking.main.presentation.favorite.AddImageCollection.AddImageInCollectionScreen
import com.example.h5traveloto_booking.main.presentation.data.dto.SearchRoomType.SearchRoomTypeDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.Booking.UserBookingDTO
import com.example.h5traveloto_booking.main.presentation.favorite.AllFavorite.AllFavoriteScreen
import com.example.h5traveloto_booking.main.presentation.favorite.DetailCollection.DetailCollectionScreen
import com.example.h5traveloto_booking.main.presentation.favorite.UpdateCollectionScreen.UpdateCollectionScreen
import com.example.h5traveloto_booking.payment.WebViewScreen2
import com.example.h5traveloto_booking.main.presentation.map.LocationProvider
import com.example.h5traveloto_booking.payment.PaymentFailed.PaymentFailedScreen
import com.example.h5traveloto_booking.payment.PaymentSuccess.PaymentSuccessScreen
import com.example.h5traveloto_booking.share.TxnShare
import com.example.h5traveloto_booking.share.shareDataHotelDetail
import com.example.h5traveloto_booking.share.shareHotelDataViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun AppNavigation(startDestination : String ,
                    paymentViewModel :BookingScreenViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
//    LocationProvider.initLocationProvider(LocalContext.current)

    NavHost (
        navController = navController,
        startDestination = startDestination

    ) {
        composable(route = Screens.SignUpScreen.name) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screens.LoginScreen.name) {
            LoginScreen(navController = navController )
        }
        composable(route = Screens.MainScreen.name  ) {
            MainScreen(navController =navController)
        }
        composable(route = Screens.ListHotels.name  ) {
            ListHotels(navController = navController)
        }
        composable(route = Screens.PersonalInformationScreen.name) {
            PersonalInformationScreen(navController = navController)
        }
        composable(route = Screens.ListRooms.name) {
            ListRooms(navController = navController)
        }
        composable(route = Screens.HotelDetailsScreen.name  ) {
            HotelDetailsScreen(navController =navController)
        }
        composable(route = Screens.UpdateInformationScreen.name ) {
            UpdateInformationScreen(navController = navController)
        }
        composable(route = Screens.ChangePasswordScreen.name ) {
            ChangePasswordScreen(navController = navController)
        }
        composable(route = Screens.ChatScreen.name ) {
            ChatScreen(navController = navController)
        }
        composable(route = Screens.AllFavoriteScreen.name ) {
            AllFavoriteScreen(navController = navController)
        }
        composable(route ="${Screens.ListPolicies.name}/{Object}" ) {
            backStackEntry ->
            val Object = Gson().fromJson(backStackEntry.arguments?.getString("Object"), HotelDetailsDTO::class.java)
            ListPolicies(navController = navController, Object = Object.data)

        }
        composable(route = Screens.ListReviews.name ) {
            ListReviews(navController = navController)
        }
        composable(route = Screens.AddCollectionScreen.name ) {
            AddCollectionScreen(navController = navController)
        }
        composable(route = "${Screens.AddHotelInCollectionScreen.name}/{CollectionId}/{new}"){
            backStackEntry ->
            val collectionId = backStackEntry.arguments?.getString("CollectionId")
            val isNew = Gson().fromJson(backStackEntry.arguments?.getString("new"),Boolean::class.java)
            if(collectionId!=null)
                AddHotelInCollectionScreen(navController = navController,collectionId, new =isNew )
        }
        composable(route = "${Screens.AddImageInCollectionScreen.name}/{CollectionName}") {
            backStackEntry->
            val collectionName = backStackEntry.arguments?.getString(("CollectionName"))
            if(collectionName!=null){
                AddImageInCollectionScreen(navController = navController,collectionName)
            }
        }
        composable(route = "${Screens.UpdateCollectionScreen.name}/{collectionName}/{collectionId}/{collection}") {
            backStackEntry->
            val collectionId = backStackEntry.arguments?.getString("collectionId")
            val collectionName = backStackEntry.arguments?.getString("collectionName")
            val Collection = Gson().fromJson(backStackEntry.arguments?.getString("collection"),Data::class.java)

            if(collectionId!=null&&collectionName!=null)
            UpdateCollectionScreen(navController = navController,
                collectionId = collectionId,
                collectionName = collectionName,
                collection = Collection)
        }
        composable(route="detailcollection/{CollectionID}/{CollectionName}/{Collection}")
         {
            backStackEntry ->
            val collectionID = backStackEntry.arguments?.getString("CollectionID")
            val collectionName = backStackEntry.arguments?.getString("CollectionName")
             val Collection = Gson().fromJson(backStackEntry.arguments?.getString("Collection"),Data::class.java)
            Log.d("hehehee",collectionID.toString())
            if (collectionID != null&&collectionName!=null&&Collection!=null ) {
                DetailCollectionScreen(navController = navController,
                    collectionID = collectionID,
                    collectionName =collectionName,
                    collection = Collection)
            }
        }
        composable(route = "${Screens.BookingDetailsFillingScreen.name}/{bookingData}") { backstabEntry ->
            val bookingData = Gson().fromJson(backstabEntry.arguments?.getString("bookingData"), CreateBookingDTO::class.java)
            BookingDetailsFillingScreen(navController = navController, bookingData = bookingData)
        }
        composable(route = "${Screens.BookingDetailsReviewScreen.name}/{bookingData}") { backstabEntry ->
            val bookingData = Gson().fromJson(backstabEntry.arguments?.getString("bookingData"), CreateBookingDTO::class.java)
            BookingReviewScreen(navController = navController, bookingData = bookingData)
        }
        composable(route = "${Screens.BookingDetailsScreen.name}/{bookingData}") { backstabEntry ->
            val bookingData = Gson().fromJson(backstabEntry.arguments?.getString("bookingData"), UserBookingDTO::class.java)
            BookingDetailsScreen(navController = navController, userBookingData = bookingData)
        }
        composable(Screens.PaymentSuccessScreen.name){
            PaymentSuccessScreen(navController = navController)
        }
        composable(Screens.PaymentFailedScreen.name){
            PaymentFailedScreen(navController = navController)
        }
        composable(Screens.AboutUsScreen.name){
            AboutUsScreen(navController = navController)
        }
        composable("webview/{url}"){
                backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")?:""
            Log.d("hehe1",url.toString())
            //  WebViewScreen2(url = url, tmnCode = "CXE5IZGS",scheme="resultactivity",isSandbox = true)
            val onPaymentResult: (String) -> Unit = { paymentResult ->
                // Xử lý kết quả thanh toán tại đây
                when (paymentResult) {
                    "SuccessBackAction" -> {
                        // Xử lý khi thanh toán thành công
                        navController.navigate(Screens.PaymentSuccessScreen.name)
                        //navController.popBackStack()
                    }
                    "FaildBackAction" -> {
                        // Xử lý khi thanh toán thất bại
                        Log.d("URL","hehe")
                        paymentViewModel.cancelPayment(TxnShare.TxnID)
                        navController.navigate(Screens.PaymentFailedScreen.name)
                        //navController.navigate(Screens.AccountScreen.name)
                        //  navController.navigateUp()
                        val bookingData = CreateBookingDTO(
                            hotelId = shareDataHotelDetail.getHotelId(),
                            roomTypeId = shareDataHotelDetail.getRoomTypeId(),
                            roomQuantity = shareDataHotelDetail.getRoomQuantity(),
                            adults = shareDataHotelDetail.getAdults(),
                            children = shareDataHotelDetail.getChildren(),
                            startDate = shareDataHotelDetail.getStartDateString(),
                            endDate = shareDataHotelDetail.getEndDateString()
                        )
                      //  navController.popBackStack(route = "${Screens.BookingDetailsReviewScreen.name}/${Gson().toJson(bookingData)}",
                      //      inclusive = true)
                      //  navController.navigate("${Screens.BookingDetailsReviewScreen.name}/${Gson().toJson(bookingData)}")
                      //  paymentViewModel.cancelPayment(txnId = TxnShare.TxnID)
                        // navController.navigate(Screens.AccountScreen.name)
                    }
                    "WebBackAction" -> {
                        // Xử lý khi quay lại từ web
                        navController.navigateUp()
                    }
                }
            }
            WebViewScreen3(url = url, scheme = "resultactivity",onPaymentResult = onPaymentResult, navController = navController)
        }

        composable(route ="${Screens.RoomDetailsScreen.name}/{Object}" ) {
                backStackEntry ->
            val Object = Gson().fromJson(backStackEntry.arguments?.getString("Object"), com.example.h5traveloto_booking.main.presentation.data.dto.SearchRoomType.Data::class.java)
            RoomDetailsScreen(navController = navController, Object = Object)

        }
        composable(route = Screens.AllChatScreen.name ) {
            AllChatScreen(navController = navController)
        }
        composable(route = Screens.ForgotPasswordScreen.name ) {
            ForgotPasswordScreen(navController = navController)
        }

    }
}