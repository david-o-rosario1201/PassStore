@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package edu.ucne.passstore.presentation.home

import android.content.Context
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
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import edu.ucne.passstore.R
import edu.ucne.passstore.data.local.entities.CuentaEntity
import edu.ucne.passstore.presentation.components.AppTheme
import edu.ucne.passstore.presentation.components.SecurityDialog
import edu.ucne.passstore.presentation.navigation.BottomNavigationBar
import edu.ucne.passstore.presentation.settings.SettingUiEvent
import edu.ucne.passstore.presentation.settings.SettingUiState
import edu.ucne.passstore.presentation.settings.SettingViewModel
import java.util.Locale


@Composable
fun HomeScreen(
    context: Context,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    goViewSubcuentaScreen: (Int) -> Unit,
    settingViewModel: SettingViewModel = hiltViewModel()
){
    val homeUiState by  homeViewModel.uiState.collectAsState()
    val settingUiState by settingViewModel.uiState.collectAsState()

    AppTheme{
        HomeBodyScreen(
            context = context,
            homeUiState = homeUiState,
            settingUiState = settingUiState,
            goViewSubcuentaScreen = goViewSubcuentaScreen,
            navHostController = navHostController,
            onSettingEvent = settingViewModel::onEvent,
            onHomeEvent = homeViewModel::onEvent
        )
    }
}

@Composable
fun HomeBodyScreen(
    context: Context,
    homeUiState: HomeUiState,
    settingUiState: SettingUiState,
    goViewSubcuentaScreen: (Int) -> Unit,
    navHostController: NavHostController,
    onSettingEvent: (SettingUiEvent) -> Unit,
    onHomeEvent: (HomeUiEvent) -> Unit
) {
    var currentLocal by remember { mutableStateOf(Locale.getDefault().language) }

    LaunchedEffect(settingUiState){

        if(settingUiState.codeSucceeded){
            goViewSubcuentaScreen(homeUiState.cuentaIdSelected)
        }
    }
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
                                text = context.getString(R.string.hello_user, userName),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = context.getString(R.string.subtitle),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                                )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
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
                        context = context,
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
                if(homeUiState.subcuentas.isEmpty()){
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
                            homeUiState.cuentas.filter { cuenta ->
                                homeUiState.subcuentas.any { subcuenta ->
                                    subcuenta.cuentaId == cuenta.cuentaId
                                }
                            }){ cuenta ->
                            CuentaRow(
                                it = cuenta,
                                cuentas = homeUiState.cuentas,
                                onSettingEvent = onSettingEvent,
                                onHomeEvent = onHomeEvent
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

                if(settingUiState.showSecurityCode){
                    SecurityDialog(
                        message = "Estas intentando mostrar tus datos personales, para continuar ingrese el código de seguridad.",
                        context = context,
                        uiState = settingUiState,
                        onEvent = onSettingEvent
                    )
                }
            }
        }
    }
}

@Composable
fun CuentaRow(
    it: CuentaEntity,
    cuentas: List<CuentaEntity>,
    onSettingEvent: (SettingUiEvent) -> Unit,
    onHomeEvent: (HomeUiEvent) -> Unit
){
    val context = LocalContext.current
    val cuenta = cuentas.find { cuentaLocal ->
        cuentaLocal.cuentaId == it.cuentaId
    }
    val resIdItem = context.resources.getIdentifier(cuenta?.iconoResName, "drawable", context.packageName)
    Card(
        onClick = {
            onSettingEvent(SettingUiEvent.ShowSecurityCode(true))
            onHomeEvent(HomeUiEvent.CuentaIdSelected(it.cuentaId ?: 0))
        },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeBodyScreenPreview(){
    HomeBodyScreen(
        context = LocalContext.current,
        homeUiState = HomeUiState(),
        settingUiState = SettingUiState(),
        goViewSubcuentaScreen = {},
        navHostController = NavHostController(LocalContext.current),
        onSettingEvent = {},
        onHomeEvent = {}
    )
}