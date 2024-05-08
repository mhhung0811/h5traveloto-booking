package com.example.h5traveloto_booking.main.presentation.schedule.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.h5traveloto_booking.theme.GreenColor
import com.example.h5traveloto_booking.theme.Grey500Color
import com.example.h5traveloto_booking.theme.Grey50Color
import com.example.h5traveloto_booking.theme.PrimaryColor
import com.example.h5traveloto_booking.ui_shared_components.YSpacer

@Composable
public fun BookingCard(
    isStatusVisible: Boolean
) {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = Grey50Color,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
//            .clip(shape = RoundedCornerShape(8.dp))
    ) {
        Row (
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            AsyncImage(
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .clip(shape = RoundedCornerShape(4.dp)),
                model = "https://cdn.vietnambiz.vn/2019/11/4/dd32d9b188d86d6d8dc40d1ff9a0ebf6-15728512315071030248829.jpg",
                contentDescription = "Cover Image",
                contentScale = ContentScale.Crop
            )
            Column (
                modifier = Modifier.
                    fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ){
                Row {
                    Text(
                        modifier = Modifier.
                            fillMaxWidth(0.5f),
                        text = "Asteria Hotel",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "$" + 164,
                            color = PrimaryColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = " /đêm",
                            color = Grey500Color,
                            fontSize = 12.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
                YSpacer(height = 5)
                Row {
                    Text(
                        text = "Wilora NT 0872, Australia",
                        fontSize = 12.sp,
                        color = Grey500Color,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                YSpacer(height = 5)
                Row {
                    Text(
                        text = "19 October 2022",
                        color = Grey500Color,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth(0.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    if (isStatusVisible) {
                        Text(
                            text = "Thành công",
                            color = GreenColor,
                            fontSize = 12.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}