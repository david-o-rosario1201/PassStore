package edu.ucne.passstore.presentation.subcuenta

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.passstore.R
import edu.ucne.passstore.data.local.entities.SubcuentaEntity
import edu.ucne.passstore.presentation.components.AppTheme
import edu.ucne.passstore.presentation.components.ConfirmModal
import edu.ucne.passstore.presentation.components.PasswordVisibilityToggle
import edu.ucne.passstore.presentation.components.SecureScreen
import edu.ucne.passstore.presentation.components.SuccessModal
import edu.ucne.passstore.presentation.components.copySensitiveText
import edu.ucne.passstore.presentation.settings.SettingUiEvent
import edu.ucne.passstore.presentation.settings.SettingUiState
import edu.ucne.passstore.presentation.settings.SettingViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ViewSubcuentaScreen(
    cuentaId: Int,
    context: Context,
    goBack: () -> Unit,
    subcuentaViewModel: SubcuentaViewModel = hiltViewModel(),
    settingViewModel: SettingViewModel = hiltViewModel()
){
    val subcuentaUiState by subcuentaViewModel.uiState.collectAsState()
    val settingUiState by settingViewModel.uiState.collectAsState()
    AppTheme {
        SecureScreen{
            ViewSubcuentaBodyScreen(
                cuentaId = cuentaId,
                subcuentaUiState = subcuentaUiState,
                settingUiState = settingUiState,
                onSettingEvent = settingViewModel::onEvent,
                onSubcuentaEvent = subcuentaViewModel::onEvent,
                context = context,
                goBack = goBack
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewSubcuentaBodyScreen(
    cuentaId: Int,
    subcuentaUiState: SubcuentaUiState,
    settingUiState: SettingUiState,
    onSettingEvent: (SettingUiEvent) -> Unit,
    onSubcuentaEvent: (SubcuentaUiEvent) -> Unit,
    context: Context,
    goBack: () -> Unit
){
    LaunchedEffect(Unit){
        onSubcuentaEvent(SubcuentaUiEvent.CuentaIdSelected(cuentaId))
    }

    val resIdItem = remember(subcuentaUiState.cuenta?.iconoResName){
        subcuentaUiState.cuenta?.iconoResName?.let{ iconName ->
            context.resources.getIdentifier(iconName, "drawable", context.packageName)
        }
    } ?: 0

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row {
                            if(resIdItem != 0){
                                Image(
                                    painter = painterResource(id = resIdItem),
                                    contentDescription = subcuentaUiState.cuenta?.nombre,
                                    modifier = Modifier.size(40.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                            } else{
                                Icon(
                                    imageVector = Icons.Default.HourglassEmpty,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = subcuentaUiState.cuenta?.nombre ?: "",
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = goBack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
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
            },
        ){ innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(subcuentaUiState.subcuentas.isEmpty()){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                            painter = painterResource(R.drawable.cajavacia),
                            contentDescription = context.getString(R.string.empty_box_description),
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = context.getString(R.string.empty_box_text),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.width(300.dp)
                        )
                    }
                } else{
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 24.dp)
                    ){
                        items(subcuentaUiState.subcuentas){ subcuenta ->
                            SubcuentaRow(
                                it = subcuenta,
                                subcuentaUiState = subcuentaUiState,
                                onSettingEvent = onSettingEvent,
                                onSubcuentaEvent = onSubcuentaEvent,
                                context = context
                            )
                        }
                    }
                }

                if(settingUiState.showConfirmModal){
                    ConfirmModal(
                        question = context.getString(R.string.confirm_modal_delete_question),
                        context = context,
                        onEvent = onSettingEvent,
                        onConfirm = {
                            onSubcuentaEvent(SubcuentaUiEvent.Delete)
                            onSettingEvent(SettingUiEvent.DismissAllModals)
                            onSettingEvent(SettingUiEvent.ShowSuccessModal(true))
                        }
                    )
                }
                if(settingUiState.showSuccessModal){
                    SuccessModal(
                        title = context.getString(R.string.action_success),
                        onEvent = onSettingEvent
                    )
                }
                if(subcuentaUiState.showEditModal){
                    EditSubcuentaModal(
                        context = context,
                        uiState = subcuentaUiState,
                        onEvent = onSubcuentaEvent,
                        onClick = {
                            onSubcuentaEvent(SubcuentaUiEvent.Save)
                            onSettingEvent(SettingUiEvent.ShowSuccessModal(true))
                        },
                        onDismiss = { onSubcuentaEvent(SubcuentaUiEvent.ShowEditModal(false)) }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun SubcuentaRow(
    it: SubcuentaEntity,
    subcuentaUiState: SubcuentaUiState,
    onSettingEvent: (SettingUiEvent) -> Unit,
    onSubcuentaEvent: (SubcuentaUiEvent) -> Unit,
    context: Context
){
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var passwordVisible by remember { mutableStateOf(false) }
//    val clipboardManager = LocalClipboardManager.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .heightIn(min = 70.dp)
    ){
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                //.padding(horizontal = 30.dp, vertical = 20.dp),
//                horizontalArrangement = Arrangement.End
//            ){
//                Surface(
//                    modifier = Modifier.size(32.dp),
//                    shape = RoundedCornerShape(50.dp),
//                    color = Color(0xFFE0E0E0)
//                ){
//                    //Boton cerrar
//                    IconButton(onClick = {}) {
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = null,
//                            tint = Color.Black
//                        )
//                    }
//                }
//            }

            //Contenido
            Text(
                text = "Usuario",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp)
            )

            OutlinedTextField(
                value = it.nombreUsuario,
                onValueChange = {},
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                shape = RoundedCornerShape(10.dp),
                enabled = false,
                trailingIcon = {
                    IconButton(onClick = {
//                        clipboardManager.setText(AnnotatedString("example@gmial.com"))
                        copySensitiveText(context, it.nombreUsuario)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )

            Text(
                text = "Contraseña",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp)
            )

            OutlinedTextField(
                value = it.password,
                onValueChange = {},
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                shape = RoundedCornerShape(10.dp),
                enabled = false,
                trailingIcon = {
                   Row{
                       PasswordVisibilityToggle(
                           passwordVisible = passwordVisible
                       ) {
                           passwordVisible = !passwordVisible
                       }

                       IconButton(onClick = {
                           copySensitiveText(context, it.password)
                       }) {
                           Icon(
                               imageVector = Icons.Default.ContentCopy,
                               contentDescription = null,
                               tint = MaterialTheme.colorScheme.onBackground
                           )
                       }
                   }
                },
                visualTransformation = if(passwordVisible){
                    VisualTransformation.None
                } else{
                    PasswordVisualTransformation()
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Button(
                    onClick = {
                        onSubcuentaEvent(SubcuentaUiEvent.SubcuentaIdSelected(it.subcuentaId ?: 0))
                        onSettingEvent(SettingUiEvent.ShowConfirmModal(true))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(
                        text = "Eliminar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = {
                        onSubcuentaEvent(SubcuentaUiEvent.SubcuentaIdSelected(it.subcuentaId ?: 0))
                        onSubcuentaEvent(SubcuentaUiEvent.ShowEditModal(true))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Editar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}