package edu.ucne.passstore.presentation.settings

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import edu.ucne.passstore.R

@Composable
fun UserViewModal(
    uiState: SettingUiState,
    context: Context,
    onDismiss: () -> Unit,
    onEvent: (SettingUiEvent) -> Unit
){
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var textFieldEnabled by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ){
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                //Botón cerrar (x)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = RoundedCornerShape(50.dp),
                        color = Color(0xFFE0E0E0)
                    ){
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }

                Image(
                    painter = painterResource(R.drawable.user_settings),
                    contentDescription = null,
                    modifier = Modifier
                        .size(125.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF11998E),
                                    Color(0xFF191654)
                                )
                            )
                        )
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                //Campos
                OutlinedTextField(
                    label = { Text("Usuario") },
                    value = uiState.userName,
                    onValueChange = {
                        onEvent(SettingUiEvent.UserNameChanged(it))
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false
                    ),
                    enabled = textFieldEnabled
                )

                OutlinedTextField(
                    label = { Text("Desde") },
                    value = uiState.userDateRegister,
                    onValueChange = {},
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false
                    ),
                    enabled = false
                )

                if(!textFieldEnabled){
                    Button(
                        onClick = {
                            textFieldEnabled = !textFieldEnabled
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.width(150.dp)
                    ) {
                        Text(
                            text = "Modificar",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                if(textFieldEnabled){
                    Row {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                text = "Cancelar",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        Button(
                            onClick = { onEvent(SettingUiEvent.SetUserInfo(uiState.userName)) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                text = "Guardar",
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
}