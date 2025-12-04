package edu.ucne.passstore.presentation.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.ucne.passstore.R
import edu.ucne.passstore.biometricauth.BiometricPromptManager
import edu.ucne.passstore.presentation.settings.SettingUiEvent
import edu.ucne.passstore.presentation.settings.SettingUiState

@Composable
fun BiometricModalComponent(
    context: Context,
    promptManager: BiometricPromptManager,
    uiState: SettingUiState,
    onEvent: (SettingUiEvent) -> Unit,
    onDismiss: () -> Unit
){
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ){
            Column(
                modifier = Modifier.padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                //Titulo
                Text(
                    text = context.getString(R.string.path_way),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Button(
                        onClick = {
                            onEvent(SettingUiEvent.ShowSecurityCode(true))
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.size(width = 110.dp, height = 100.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ){
                            Image(
                                painter = painterResource(R.drawable.contrasena),
                                contentDescription = null,
                                modifier = Modifier.size(45.dp)
                            )
                            Text(
                                text = context.getString(R.string.pin_way),
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                            if(uiState.biometricAuth){
                                promptManager.showBiometricPrompt(
                                    title = context.getString(R.string.finger_print_auth),
                                    description = context.getString(R.string.fingerprint_auth_description)
                                )
                            } else{
                                onEvent(SettingUiEvent.ShowBiometricModal(false))
                                onEvent(SettingUiEvent.ShowBiometricAuthDisabledModal(true))
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.size(width = 120.dp, height = 100.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Image(
                                painter = painterResource(R.drawable.escaneo_seguridad),
                                contentDescription = null,
                                modifier = Modifier.size(45.dp)
                            )

                            Text(
                                text = context.getString(R.string.biometric_way),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.surface,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                softWrap = true,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}