package edu.ucne.passstore.presentation.subcuenta

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import edu.ucne.passstore.R
import edu.ucne.passstore.presentation.components.PasswordVisibilityToggle

@Composable
fun EditSubcuentaModal(
    context: Context,
    uiState: SubcuentaUiState,
    onEvent: (SubcuentaUiEvent) -> Unit,
    onClick: () -> Unit,
    onDismiss: () -> Unit
){
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    val camposCompletos = uiState.nombreUsuario.isNotBlank() && uiState.password.isNotBlank()

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

                //Icono'
                Image(
                    painter = painterResource(R.drawable.blindaje),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                //Titulo
                Text(
                    text = context.getString(R.string.restrict_access),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                //Campos
                OutlinedTextField(
                    label = { Text(context.getString(R.string.user_label)) },
                    value = uiState.nombreUsuario,
                    onValueChange = {
                        onEvent(SubcuentaUiEvent.NombreChanged(it))
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .focusRequester(focusRequester)
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(
                                FocusDirection.Next
                            )
                        }
                    )
                )

                OutlinedTextField(
                    label = {Text(context.getString(R.string.password_label))},
                    value = uiState.password,
                    onValueChange = {
                        onEvent(SubcuentaUiEvent.PasswordChanged(it))
                    },
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .focusRequester(focusRequester)
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true,
                    maxLines = 1,
                    trailingIcon = {
                        PasswordVisibilityToggle(
                            passwordVisible = passwordVisible
                        ){
                            passwordVisible = !passwordVisible
                        }
                    },
                    visualTransformation = if(passwordVisible){
                        VisualTransformation.None
                    } else{
                        PasswordVisualTransformation()
                    }
                )
                if (!camposCompletos) {
                    Text(
                        text = context.getString(R.string.must_complete_all_fields),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        onClick()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.width(150.dp),
                    enabled = camposCompletos
                ) {
                    Text(
                        text = context.getString(R.string.save),
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