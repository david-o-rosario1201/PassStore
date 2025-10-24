package edu.ucne.passstore.presentation.components

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.ucne.passstore.R
import edu.ucne.passstore.presentation.settings.SettingUiEvent
import edu.ucne.passstore.presentation.settings.SettingUiState
import kotlinx.coroutines.delay

@Composable
fun NewSecurityDialog(
    title: String,
    context: Context,
    uiState: SettingUiState,
    onEvent: (SettingUiEvent) -> Unit
){
    val pinNewValues = remember {
        mutableStateListOf("", "", "", "", "", "")
    }
    val pinConfirmValues = remember {
        mutableStateListOf("", "", "", "", "", "")
    }
    val focusNewRequesters = List(6) {FocusRequester()}
    val focusConfirmRequesters = List(6) {FocusRequester()}
    val haptic = LocalHapticFeedback.current
    val shake = remember { Animatable(0f) }

    LaunchedEffect(uiState.showEmptyMessage,
        uiState.showIncompleteMessage, uiState.checkCodeIsIncorrect){

        shake.snapTo(0f)
        shake.animateTo(
            targetValue = 1f,
            animationSpec = repeatable(
                iterations = 3,
                animation = tween(80, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        shake.snapTo(0f)
        delay(1000)
        onEvent(SettingUiEvent.ResetErrorMessages)
    }

    Dialog(onDismissRequest = {}) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ){
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                //Icono
                Image(
                    painter = painterResource(R.drawable.blindaje),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                //Titulo
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                //Ingrese el nuevo codigo
                Text(
                    text = context.getString(R.string.insert_new_code),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campos de código (PIN de 6 dígitos como ejemplo)
                Row(
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = (shake.value - 0.5f) * 20f
                        },
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    repeat(6){ index ->
                        OutlinedTextField(
                            value = pinNewValues[index],
                            onValueChange = { newValue ->
                                if(newValue.length <= 1){
                                    pinNewValues[index] = newValue

                                    // Si se escribió un dígito, pasar al siguiente campo
                                    if(newValue.isNotEmpty() && index < 5){
                                        focusNewRequesters[index + 1].requestFocus()
                                    }

                                    // Si está vacío y no es el primero, volver al anterior
                                    if(newValue.isEmpty() && index > 0){
                                        focusNewRequesters[index - 1].requestFocus()
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(42.dp, 56.dp)
                                .focusRequester(focusNewRequesters[index]),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = if(pinNewValues[index].isNotEmpty()) Color.Black else MaterialTheme.colorScheme.onSurface,
                                focusedBorderColor = if (uiState.errorState) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if (uiState.errorState) MaterialTheme.colorScheme.error else Color.Gray,
                                focusedContainerColor = if (pinNewValues[index].isNotEmpty()) Color(0xFFE8F0FF) else MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = if (pinNewValues[index].isNotEmpty()) Color(0xFFE8F0FF) else MaterialTheme.colorScheme.background
                            )

                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Repita el código
                Text(
                    text = context.getString(R.string.repeat_code),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campos de código (PIN de 6 dígitos como ejemplo)
                Row(
                    modifier = Modifier
                        .graphicsLayer {
                            translationX = (shake.value - 0.5f) * 20f
                        },
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    repeat(6){ index ->
                        OutlinedTextField(
                            value = pinConfirmValues[index],
                            onValueChange = { newValue ->
                                if(newValue.length <= 1){
                                    pinConfirmValues[index] = newValue

                                    // Si se escribió un dígito, pasar al siguiente campo
                                    if(newValue.isNotEmpty() && index < 5){
                                        focusConfirmRequesters[index + 1].requestFocus()
                                    }

                                    // Si está vacío y no es el primero, volver al anterior
                                    if(newValue.isEmpty() && index > 0){
                                        focusConfirmRequesters[index - 1].requestFocus()
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(42.dp, 56.dp)
                                .focusRequester(focusConfirmRequesters[index]),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = if(pinConfirmValues[index].isNotEmpty()) Color.Black else MaterialTheme.colorScheme.onSurface,
                                focusedBorderColor = if (uiState.errorState) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if (uiState.errorState) MaterialTheme.colorScheme.error else Color.Gray,
                                focusedContainerColor = if (pinConfirmValues[index].isNotEmpty()) Color(0xFFE8F0FF) else MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = if (pinConfirmValues[index].isNotEmpty()) Color(0xFFE8F0FF) else MaterialTheme.colorScheme.background
                            )

                        )
                    }
                }

                if(uiState.checkCodeIsIncorrect){
                    Text(
                        text = context.getString(R.string.incorrect_code),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                if(uiState.showEmptyMessage){
                    Text(
                        text = context.getString(R.string.must_insert_code),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                if(uiState.showIncompleteMessage){
                    Text(
                        text = context.getString(R.string.complete_all_digits),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                if(uiState.showCodesAreNotEqualsMessage){
                    Text(
                        text = context.getString(R.string.codes_are_different),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                Spacer(modifier = Modifier.height(20.dp))

                //Botones
                Row{
                    Button(
                        onClick = { onEvent(SettingUiEvent.DismissAllModals) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.width(140.dp)
                    ) {
                        Text(
                            text = context.getString(R.string.cancel_button),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            onEvent(SettingUiEvent.CheckCodesAreNotEmpty(pinNewValues, pinConfirmValues))

                            if(pinNewValues.all{ it.isNotEmpty() } && pinConfirmValues.all { it.isNotEmpty() }){
                                onEvent(SettingUiEvent.CheckCodesAreEquals(pinNewValues, pinConfirmValues))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.width(140.dp)
                    ) {
                        Text(
                            text = context.getString(R.string.accept_button),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}