package edu.ucne.passstore.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.ucne.passstore.R
import edu.ucne.passstore.presentation.navigation.BottomNavigationBar
import java.util.Locale

@Composable
fun SettingScreen(navHostController: NavHostController){
    var switchState by remember { mutableStateOf(false) }

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

    val colors = if (isDarkMode) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors
    ){
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
                        BottomNavigationBar(navHostController = navHostController)
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
                        text = "Ajustes",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
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
                                .clickable {  }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically){
                                Image(
                                    painter = painterResource(R.drawable.user_settings),
                                    contentDescription = "User",
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
                                        text = "Juan Pérez",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    )
                                    Text(
                                        text = "!Bienvenido de nuevo!",
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
                    SectionTitle("Seguridad")
                    SettingsCard {
                        SettingRow(
                            title = "Código de seguridad",
                            value = "",
                            onClick = { }
                        )
                        SettingSwitchRow(
                            title = "Desbloqueo biométrico",
                            checked = switchState,
                            onCheckedChange = { switchState = it }
                        )
                        SettingRow(
                            title = "Tiempo de bloqueo automático",
                            value = "10 min",
                            onClick = { }
                        )
                    }

                    // --- Sección Personalización ---
                    SectionTitle("Personalización")
                    SettingsCard {
                        SettingSwitchRow(
                            title = "Tema claro/oscuro",
                            leftLabel = "Oscuro",
                            rightLabel = "Claro",
                            checked = switchState,
                            onCheckedChange = { switchState = it }
                        )
                        SettingSwitchRow(
                            title = "Idioma",
                            leftLabel = "Inglés",
                            rightLabel = "Español",
                            checked = switchState,
                            onCheckedChange = { switchState = it }
                        )
                    }

                    // --- Sección Legal ---
                    SectionTitle("Legal y Acerca de")
                    SettingsCard {
                        SettingRow(
                            title = "Términos y condiciones",
                            onClick = { }
                        )
                        SettingRow(
                            title = "Información de la app",
                            onClick = { }
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
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
            color = Color.Black
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
                color = Color.Black
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
    onCheckedChange: (Boolean) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = title,
            style = TextStyle(
                fontSize = 13.sp,
                color = Color.Black
            )
        )
        Row(verticalAlignment = Alignment.CenterVertically){
            leftLabel?.let { Text(it, fontSize = 12.sp, color = Color.Gray) }

            if(title == "Seguridad"){
                CustomSwitchWithLabel(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    label = ""
                )
            } else{
                CustomSwitchWithLabel(
                    checked = checked,
                    onCheckedChange = onCheckedChange
                )
            }
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
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                checkedTrackColor = Color(0xFFDDDDDD),
                uncheckedThumbColor = Color.Black,
                uncheckedTrackColor = Color.White
            )
        )
    }
}