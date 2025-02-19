package com.example.h5traveloto_booking.main.presentation.favorite.AddCollection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.h5traveloto_booking.main.presentation.data.dto.Account.Avatar
import com.example.h5traveloto_booking.main.presentation.data.dto.Favorite.AddCollectionDTO
import com.example.h5traveloto_booking.main.presentation.data.dto.Favorite.CreateResponse
import com.example.h5traveloto_booking.main.presentation.domain.usecases.FavoriteUseCases
import com.example.h5traveloto_booking.util.ErrorResponse
import com.example.h5traveloto_booking.util.Result
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AddCollectionViewModel@Inject constructor(
    private val useCases:FavoriteUseCases
) : ViewModel() {
    private val createCollectionDataResponse = MutableStateFlow<Result<CreateResponse>>(Result.Idle)
    val CreateCollectionDataResponse = createCollectionDataResponse.asStateFlow()

    fun createCollection(name:String,image: Avatar?) = viewModelScope.launch{
        val data = AddCollectionDTO(name=name, cover = image)
        useCases.createCollectionUseCase(data).onStart {
            createCollectionDataResponse.value = Result.Loading
        }.catch {
            if(it is HttpException){
                Log.d("AddCollection ViewModel", "catch")
                //Log.d("ChangePassword ViewModel E", it.message.toString())
                Log.d("AddCollection ViewModel", "hehe")
                val errorResponse = Gson().fromJson(it.response()?.errorBody()!!.string(), ErrorResponse::class.java)
                Log.d("AddCollection ViewModel Error", errorResponse.message)
                Log.d("AddCollection ViewModel Error", errorResponse.log)
                createCollectionDataResponse.value = Result.Error(errorResponse.message)
            }
            else if (it is Exception) {
                createCollectionDataResponse.value = Result.Error("errorResponse.message")
                Log.d("AddHotel ViewModel", it.javaClass.name)
            }
        }.collect{
            createCollectionDataResponse.value = Result.Success(it)
        }
    }
}