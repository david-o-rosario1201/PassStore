@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package edu.ucne.passstore.presentation.home

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import edu.ucne.passstore.R
import edu.ucne.passstore.data.local.entities.CuentaEntity
import edu.ucne.passstore.presentation.navigation.BottomNavigationBar
import java.util.Locale


@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by  viewModel.uiState.collectAsState()
    HomeBodyScreen(
        uiState = uiState,
        navHostController = navHostController
    )
}

@Composable
fun HomeBodyScreen(
    uiState: HomeUiState,
    navHostController: NavHostController
) {
    var currentLocal by remember { mutableStateOf(Locale.getDefault().language) }

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
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.background,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Column {
                                val userName = "Juan Pérez" // esto puede venir de ViewModel o estado
                                Text(
                                    text = localizedString(R.string.hello_user, currentLocal, userName),
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = localizedString(R.string.subtitle, currentLocal),
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                                    )
                                )
                            }
                        },
                        colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary, // cambia con tema
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
                bottomBar = {
                    val barColor = MaterialTheme.colorScheme.surface
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = barColor,
                        shadowElevation = 8.dp,
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    ) {
                        BottomNavigationBar(
                            navHostController = navHostController
                        )
                    }
                }
            ){ innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    if(uiState.subcuentas.isEmpty()){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally

                        ){
                            Image(
                                painter = painterResource(R.drawable.cajavacia),
                                contentDescription = localizedString(R.string.empty_box_description, currentLocal),
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = localizedString(R.string.empty_box_text, currentLocal),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                    textAlign = TextAlign.Center,
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
                            items(
                                uiState.cuentas.filter { cuenta ->
                                    uiState.subcuentas.any { subcuenta ->
                                        subcuenta.cuentaId == cuenta.cuentaId
                                    }
                                }){ cuenta ->
                                SubcuentaRow(
                                    it = cuenta,
                                    cuentas = uiState.cuentas,
                                    onClick = {}
                                )
                            }
                        }
                    }
                    Row {
                        Button(onClick = { currentLocal = "es" }) {
                            Text("ES")
                        }
                        Button(onClick = { currentLocal = "en" }) {
                            Text("EN")
                        }
                    }

                    ThemeSwitcher(
                        isDarkMode = isDarkMode,
                        onClick = { isDarkMode = !isDarkMode }
                    )
                }
            }
        }
    }
}

@Composable
fun SubcuentaRow(
    it: CuentaEntity,
    cuentas: List<CuentaEntity>,
    onClick: (Int) -> Unit
){
    val context = LocalContext.current
    val cuenta = cuentas.find { cuentaLocal ->
        cuentaLocal.cuentaId == it.cuentaId
    }
    val resIdItem = context.resources.getIdentifier(cuenta?.iconoResName, "drawable", context.packageName)
    Card(
        onClick = {  },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .heightIn(min = 70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(id = resIdItem),
                contentDescription = cuenta?.nombre,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = cuenta?.nombre ?: "Sin nombre",
                style = TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Go",
                tint = Color.Gray,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun ThemeSwitcher(
    isDarkMode: Boolean,
    size: Dp = 40.dp,
    iconSize: Dp = size / 3,
    padding: Dp = 10.dp,
    borderWidth: Dp = 1.dp,
    parentShape: CornerBasedShape = CircleShape,
    toggleShape: CornerBasedShape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit
){
    val offset by animateDpAsState(
        targetValue = if(isDarkMode) 0.dp else size,
        animationSpec = animationSpec
    )

    Box(
        modifier = Modifier
            .width(size * 2)
            .height(size)
            .clip(shape = parentShape)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ){
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.primary)
        ){}
        Row (
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                    shape = parentShape
                )
        ){
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.Nightlight,
                    contentDescription = "Theme Icon",
                    tint = if(isDarkMode) MaterialTheme.colorScheme.secondaryContainer
                        else MaterialTheme.colorScheme.primary
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.LightMode,
                    contentDescription = "Theme Icon",
                    tint = if(isDarkMode) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
}

@Composable
fun localizedString(@androidx.annotation.StringRes resId: Int, locale: String, vararg args: Any): String {
    val context = LocalContext.current
    val configuration = context.resources.configuration
    configuration.setLocale(Locale(locale))
    val localizedContext = context.createConfigurationContext(configuration)
    return localizedContext.resources.getString(resId, *args)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeBodyScreenPreview(){
    HomeBodyScreen(
        uiState = HomeUiState(),
        navHostController = NavHostController(LocalContext.current)
    )
}