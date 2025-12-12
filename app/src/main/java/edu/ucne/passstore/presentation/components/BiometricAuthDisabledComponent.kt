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

@Composable
fun BiometricAuthDisabledComponent(
    context: Context,
    onDismiss: () -> Unit,
    onGoSettings: () -> Unit,
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
                    text = "Biometric Auth Disabled",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically){
                    Button(
                        onClick = onDismiss,
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
                                painter = painterResource(R.drawable.cancelar),
                                contentDescription = null,
                                modifier = Modifier.size(45.dp)
                            )
                            Text(
                                text = context.getString(R.string.cancel_button),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.surface,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = {
                            onDismiss()
                            onGoSettings()
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
                            verticalArrangement = Arrangement.SpaceBetween
                        ){
                            Image(
                                painter = painterResource(R.drawable.configuraciones),
                                contentDescription = null,
                                modifier = Modifier.size(45.dp)
                            )

                            Text(
                                text = context.getString(R.string.go_to_settings),
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