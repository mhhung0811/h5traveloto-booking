package com.example.h5traveloto_booking.ui_shared_components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun BoldText(text : String ) {
    Text(
        fontSize = 16.sp,
        fontWeight =  FontWeight.Bold,
        text = text
    )
}
@Composable
fun BoldText2(text : String,modifier: Modifier = Modifier) {
    Text(
        fontSize = 16.sp,
        fontWeight =  FontWeight.Bold,
        text = text,
        modifier = modifier
    )
}
@Composable
fun BoldText14(text: String ) {
    Text(
        fontSize = 14.sp,
        fontWeight =  FontWeight.Bold,
        text = text
    )
}
@Composable
fun BoldText20(text: String ) {
    Text(
        fontSize = 20.sp,
        fontWeight =  FontWeight.Bold,
        text = text
    )
}
@Composable
fun BoldText16(text: String ) {
    Text(
        fontSize = 20.sp,
        fontWeight =  FontWeight.Bold,
        text = text
    )
}
@Composable
fun BoldText24(text: String ) {
    Text(
        fontSize = 24.sp,
        fontWeight =  FontWeight.Bold,
        text = text
    )
}