package edu.ucne.passstore.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.ucne.passstore.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityDialog(
    title: String,
    message: String,
    onConfirm: (String) -> Boolean,
    onDismiss: () -> Unit
){
    val pinValues = remember {
        mutableStateListOf("", "", "", "", "", "")
    }
    val focusRequesters = List(6) {FocusRequester()}
    val haptic = LocalHapticFeedback.current
    val errorState = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {onDismiss()}) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ){
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                // Botón cerrar (X)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = RoundedCornerShape(50.dp),
                        color = Color(0xFFE0E0E0)
                    ){
                        IconButton(onClick = {onDismiss()}) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color.Black
                            )
                        }
                    }
                }
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

                //Mensaje
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campos de código (PIN de 6 dígitos como ejemplo)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    repeat(6){ index ->
                        OutlinedTextField(
                            value = pinValues[index],
                            onValueChange = { newValue ->
                                if(newValue.length <= 1){ // Solo permitir 1 dígito
                                    pinValues[index] = newValue

                                    // Si se escribió un dígito, pasar al siguiente campo
                                    if(newValue.isNotEmpty() && index < 5){
                                        focusRequesters[index + 1].requestFocus()
                                    }

                                    // Si está vacío y no es el primero, volver al anterior
                                    if(newValue.isEmpty() && index > 0){
                                        focusRequesters[index - 1].requestFocus()
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(42.dp, 56.dp)
                                .focusRequester(focusRequesters[index]),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = MaterialTheme.colorScheme.onSurface,
                                focusedBorderColor = if(errorState.value) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if(errorState.value) MaterialTheme.colorScheme.error else Color.Gray,
                                containerColor = if (pinValues[index].isNotEmpty()) Color(0xFFE8F0FF) else MaterialTheme.colorScheme.background
                            )
                        )
                    }
                }

                if(errorState.value) {
                    Text(
                        text = "Código incorrecto",
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                //Boton confirmar
                Button(
                    onClick = {
                        val code = pinValues.joinToString("")
                        val success = onConfirm(code)
                        if(!success){
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            errorState.value = true
                            pinValues.replaceAll { "" }
                        } else {
                            errorState.value = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Confirmar acceso",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}