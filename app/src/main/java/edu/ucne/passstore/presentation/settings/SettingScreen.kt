package edu.ucne.passstore.presentation.settings

import android.content.Context
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import edu.ucne.passstore.R
import edu.ucne.passstore.presentation.components.AppTheme
import edu.ucne.passstore.presentation.components.ConfirmModal
import edu.ucne.passstore.presentation.components.NewSecurityDialog
import edu.ucne.passstore.presentation.components.SecurityDialog
import edu.ucne.passstore.presentation.components.SuccessModal
import edu.ucne.passstore.presentation.navigation.BottomNavigationBar

@Composable
fun SettingScreen(
    context: Context,
    navHostController: NavHostController,
    shouldHighlightBiometric: Boolean,
    viewModel: SettingViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(shouldHighlightBiometric) {
        if (shouldHighlightBiometric) {
            viewModel.updateHighlightBiometric(true)
        }
    }

    val vmHighlight = viewModel.shouldHighlightBiometric

    AppTheme {
        SettingBodyScreen(
            shouldHighlightBiometric = vmHighlight,   // ⬅️ siempre el del ViewModel
            context = context,
            navHostController = navHostController,
            uiState = uiState,
            onEvent = viewModel::onEvent,
            viewModel = viewModel
        )
    }
}

@Composable
fun SettingBodyScreen(
    shouldHighlightBiometric: Boolean,
    context: Context,
    navHostController: NavHostController,
    uiState: SettingUiState,
    onEvent: (SettingUiEvent) -> Unit,
    viewModel: SettingViewModel
){
    val infinite = rememberInfiniteTransition()
    val pulse by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val highlightModifier = if (shouldHighlightBiometric) {
        Modifier
            .graphicsLayer {
                scaleX = pulse
                scaleY = pulse
            }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary, // Color del tema
                shape = RoundedCornerShape(12.dp)
            )
            .padding(6.dp)
    } else Modifier



    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                val barColor = MaterialTheme.colorScheme.surface
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = barColor,
                    shadowElevation = 8.dp,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ) {
                    BottomNavigationBar(
                        context = context,
                        navHostController = navHostController
                    )
                }
            }
        ){ innerPadding ->
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState, enabled = true)
            ) {
                Text(
                    text = context.getString(R.string.setting_title),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp),
                            clip = false
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onEvent(SettingUiEvent.ShowUserView(true)) }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                painter = painterResource(R.drawable.user_settings),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFF11998E),
                                                Color(0xFF191654)
                                            )
                                        )
                                    ),
                                contentScale = ContentScale.Crop,
                            )

                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = uiState.userName,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                                Text(
                                    text = context.getString(R.string.greetings),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )
                                )
                            }
                        }
                        Image(
                            painter = painterResource(R.drawable.boton_editar),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // --- Sección Seguridad ---
                SectionTitle(context.getString(R.string.security_section))
                SettingsCard {
                    SettingRow(
                        title = context.getString(R.string.security_code),
                        value = "******",
                        onClick = {
                            onEvent(SettingUiEvent.ShowSecurityCode(true))
                        }
                    )
                    SettingSwitchRow(
                        title = context.getString(R.string.biometric_auth),
                        checked = uiState.biometricAuth,
                        onCheckedChange = { isChecked ->
                            onEvent(SettingUiEvent.SetBiometricAuth(isChecked))
                            viewModel.updateHighlightBiometric(false)
                        },
                        modifier = highlightModifier
                    )
                    SettingRow(
                        title = context.getString(R.string.lock_screen_timer),
                        value = "10 min",
                        onClick = { }
                    )
                }

                // --- Sección Personalización ---
//                    SectionTitle("Personalización")
//                    SettingsCard {
//                        SettingSwitchRow(
//                            title = "Tema claro/oscuro",
//                            checked = true,
//                            onCheckedChange = { viewModel.toggleDarkMode() }
//                        )
//                        SettingSwitchRow(
//                            title = "Idioma",
//                            leftLabel = "Inglés",
//                            rightLabel = "Español",
//                            checked = switchState,
//                            onCheckedChange = { switchState = it }
//                        )
//                    }

                // --- Sección Legal ---
                SectionTitle(context.getString(R.string.about_section))
                SettingsCard {
                    SettingRow(
                        title = context.getString(R.string.terms_and_conditions),
                        onClick = { }
                    )
                    SettingRow(
                        title = context.getString(R.string.app_info),
                        onClick = { }
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))

                if (uiState.showSecurityCode) {
                    SecurityDialog(
                        context = context,
                        uiState = uiState,
                        onEvent = onEvent
                    )
                }
                if (uiState.showNewSecurityCode) {
                    NewSecurityDialog(
                        title = context.getString(R.string.restrict_access),
                        context = context,
                        uiState = uiState,
                        onEvent = onEvent
                    )
                }
                if(uiState.showConfirmModal){
                    ConfirmModal(
                        //cambiar esta pregunta
                        question = context.getString(R.string.confirm_modal_question),
                        context = context,
                        onEvent = onEvent,
                        onConfirm = {
                            onEvent(SettingUiEvent.SetPinCode)
                        }
                    )
                }
                if(uiState.showSuccessModal){
                    SuccessModal(
                        title = context.getString(R.string.security_code_updated),
                        onEvent = onEvent
                    )
                }

                if(uiState.showUserView){
                    UserViewModal(
                        uiState = uiState,
                        context = context,
                        onDismiss = {
                            onEvent(SettingUiEvent.ShowUserView(false))
                        },
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String){
    Text(
        text = title,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun SettingsCard(content: @Composable ColumnScope.() -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ){
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            content()
        }
    }
}

@Composable
fun SettingRow(
    title: String,
    value: String? = null,
    onClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = title,
            style = TextStyle(
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Row(verticalAlignment = Alignment.CenterVertically){
            value?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            IconButton(
                onClick = { onClick() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
    }
}

@Composable
fun SettingSwitchRow(
    title: String,
    leftLabel: String? = null,
    rightLabel: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier   // ⬅️ Aquí se aplica el highlight si lo mandas
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = title,
            style = TextStyle(
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Row(verticalAlignment = Alignment.CenterVertically){
            leftLabel?.let { Text(it, fontSize = 12.sp, color = Color.Gray) }
            CustomSwitchWithLabel(
                checked = checked,
                onCheckedChange = { onCheckedChange(it) }
            )
            rightLabel?.let { Text(it, fontSize = 12.sp, color = Color.Gray) }
        }
    }
}

@Composable
fun CustomSwitchWithLabel(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String = ""
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        if(label.isNotEmpty()){
            Text(
                text = if(checked) "ON" else "OFF",
                color = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                checkedTrackColor = Color(0xFFDDDDDD),
                uncheckedThumbColor = Color.Black,
                uncheckedTrackColor = Color.White
            )
        )
    }
}