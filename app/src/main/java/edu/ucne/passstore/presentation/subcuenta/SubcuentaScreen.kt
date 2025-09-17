@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package edu.ucne.passstore.presentation.subcuenta

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.passstore.presentation.components.CustomDropDownMenu
import edu.ucne.passstore.presentation.components.PasswordVisibilityToggle
import kotlinx.coroutines.delay

@Composable
fun SubcuentaScreen(
    goHome: () -> Unit,
    viewModel: SubcuentaViewModel = hiltViewModel()
){
    val uiSate by viewModel.uiState.collectAsState()
    SubcuentaBodyScreen(
        uiState = uiSate,
        onEvent = viewModel::onEvent,
        goHome = goHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubcuentaBodyScreen(
    uiState: SubcuentaUiState,
    onEvent: (SubcuentaUiEvent) -> Unit,
    goHome: () -> Unit
){
    var isDarkMode by rememberSaveable { mutableStateOf(false) }

    val LightColors = lightColorScheme(
        primary = Color(0xFF0D47A1),
        onPrimary = Color.White,
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
    )

    val DarkColors = darkColorScheme(
        primary = Color(0xFF90CAF9),
        onPrimary = Color.Black,
        background = Color.Black,
        onBackground = Color.White,
        surface = Color(0xFF121212),
        onSurface = Color.White,
    )

    val colors = if(isDarkMode) DarkColors else LightColors
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    val showBanner = uiState.errorMessage.isNotEmpty()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if(uiState.errorMessage.isEmpty() && uiState.success){
            goHome()
        }
    }

    MaterialTheme(
        colorScheme = colors
    ){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.background,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Registrar Nueva Cuenta",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(
                                bottomStart = 30.dp, bottomEnd = 30.dp
                            )
                        )
                    )
                }
            ){ innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    if (showBanner) {
                        MessageBanner(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            visible = true,
                            label = uiState.errorMessage,
                            onDismiss = { onEvent(SubcuentaUiEvent.ErrorDismiss) }
                        )
                    }

                    OutlinedTextField(
                        label = {Text("Usuario")},
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
                            autoCorrect = false,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(
                                    FocusDirection.Next
                                )
                            }
                        ),
                        singleLine = true,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        label = {Text("Contraseña")},
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
                            autoCorrect = false,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(
                                    FocusDirection.Next
                                )
                            }
                        ),
                        singleLine = true,
                        maxLines = 1,
                        trailingIcon = {
                            PasswordVisibilityToggle(
                                passwordVisible = passwordVisible
                            ) {
                                passwordVisible = !passwordVisible
                            }
                        },
                        visualTransformation = if(passwordVisible){
                            VisualTransformation.None
                        } else{
                            PasswordVisualTransformation()
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    CustomDropDownMenu(
                        uiState = uiState,
                        onEvent = onEvent
                    )

                    Text(
                        text = "Selecciona el servicio al que pertenece esta cuenta.",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF6B7280)
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                    Row {
                        Button(
                            onClick = {
                                if(uiState.nombreUsuario.isNotEmpty()
                                    || uiState.password.isNotEmpty() || uiState.cuentaId != 0){
                                    showDialog = true
                                } else {
                                    goHome()
                                }
                            },
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

                        Spacer(modifier = Modifier.width(20.dp))

                        Button(
                            onClick = {
                                onEvent(SubcuentaUiEvent.Save)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text(
                                text = "Crear",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    ConfirmationDialog(
                        showDialog = showDialog,
                        title = "Datos sin guardar",
                        message = "¿Estás seguro de que deseas eliminar esta cuenta?",
                        onConfirm = { goHome() },
                        onDismiss = { showDialog = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageBanner(
    modifier: Modifier = Modifier,
    visible: Boolean,
    label: String,
    onDismiss: () -> Unit,
    durationMillis: Long = 1000L,
    backgroundColor: Color = Color.Red,
    contentColor: Color = Color.White
){
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically(
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(0.dp),
            text = label,
            textAlign = TextAlign.Center,
            color = contentColor
        )
    }

    LaunchedEffect(visible){
        if(visible){
            delay(durationMillis)
            onDismiss()
        }
    }
}

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = title, fontWeight = FontWeight.Bold) },
            text = { Text(text = message) },
            confirmButton = {
                Button(onClick = { onConfirm() }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SubcuentaPreview(){
    SubcuentaBodyScreen(
        uiState = SubcuentaUiState(),
        onEvent = {},
        goHome = {}
    )
}