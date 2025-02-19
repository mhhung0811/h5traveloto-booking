package com.example.h5traveloto_booking.ui_shared_components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.h5traveloto_booking.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBox(modifier: Modifier, placeholder: String, label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        placeholder = { Text(placeholder) },
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier =    modifier,
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = PrimaryColor,
            containerColor = Color.Transparent,
            focusedLabelColor = PrimaryColor
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordBoxSingle(modifier: Modifier, placeholder: String, label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        singleLine = true,
        placeholder = { Text(placeholder) },
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier =    modifier,
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = PrimaryColor,
            containerColor = Color.Transparent,
            focusedLabelColor = PrimaryColor
        )
    )
}