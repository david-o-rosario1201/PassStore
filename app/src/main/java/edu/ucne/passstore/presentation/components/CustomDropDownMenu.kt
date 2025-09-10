package edu.ucne.passstore.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import edu.ucne.passstore.R
import edu.ucne.passstore.presentation.subcuenta.SubcuentaUiEvent
import edu.ucne.passstore.presentation.subcuenta.SubcuentaUiState
import kotlin.math.exp

@Composable
fun CustomDropDownMenu(
    uiState: SubcuentaUiState,
    onEvent: (SubcuentaUiEvent) -> Unit
){
    val context = LocalContext.current
    val cuentas = uiState.cuentas
    var expanded by remember { mutableStateOf(false)}
    var selectedCuenta  by remember { mutableStateOf(uiState.cuentas.firstOrNull()) }
    var textFielSize by remember { mutableStateOf(Size.Zero) }

    val icon = if(expanded){
        Icons.Filled.KeyboardArrowUp
    } else{
        Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            label = {Text("Cuenta")},
            value = selectedCuenta?.nombre ?: "Elija una cuenta",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .onGloballyPositioned { coordinates ->
                    textFielSize = coordinates.size.toSize()
                },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                val resId = context.resources.getIdentifier(selectedCuenta?.iconoResName ?: "", "drawable", context.packageName)
                val safeResId = if (resId != 0) resId else R.drawable.ic_launcher_foreground
                Image(
                    painter = painterResource(id = safeResId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                )
            },
            readOnly = true,
            singleLine = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            modifier = Modifier.width(
                with(LocalDensity.current){
                    textFielSize.width.toDp()
                }
            ).clip(RoundedCornerShape(12.dp))
        ) {
            cuentas.forEach{cuenta ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedCuenta = cuenta
                        onEvent(SubcuentaUiEvent.CuentaIdChanged(cuenta.cuentaId ?: 0))
                    },
                    leadingIcon = {
                        val resIdItem = context.resources.getIdentifier(cuenta.iconoResName, "drawable", context.packageName)
                        val safeResIdItem = if (resIdItem != 0) resIdItem else R.drawable.ic_launcher_foreground

                        Image(
                            painter = painterResource(id = safeResIdItem),
                            contentDescription = null,
                            modifier = Modifier
                                .width(24.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                    },
                    text = {
                        Text(
                            text = cuenta.nombre,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                )
            }
        }
    }
}