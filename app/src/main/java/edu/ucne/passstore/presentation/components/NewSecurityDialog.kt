package edu.ucne.passstore.presentation.components

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.ucne.passstore.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewSecurityDialog(
    title: String,
    onConfirm: (String) -> Boolean,
    onDismiss: () -> Unit
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
    val errorState = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
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
                    text = "Ingrese el nuevo código",
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
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = MaterialTheme.colorScheme.onSurface,
                                focusedBorderColor = if(errorState.value) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if(errorState.value) MaterialTheme.colorScheme.error else Color.Gray,
                                containerColor = if (pinNewValues[index].isNotEmpty()) Color(0xFFE8F0FF) else MaterialTheme.colorScheme.background
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Repita el código
                Text(
                    text = "Repita el código",
                    color = Color.Black,
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
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = MaterialTheme.colorScheme.onSurface,
                                focusedBorderColor = if(errorState.value) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = if(errorState.value) MaterialTheme.colorScheme.error else Color.Gray,
                                containerColor = if (pinConfirmValues[index].isNotEmpty()) Color(0xFFE8F0FF) else MaterialTheme.colorScheme.background
                            )
                        )
                    }
                }

                if(errorState.value){
                    Text(
                        text = "Los códigos no coinciden",
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                //Botones
                Row{
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.width(140.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.width(140.dp)
                    ) {
                        Text(
                            text = "Aceptar",
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