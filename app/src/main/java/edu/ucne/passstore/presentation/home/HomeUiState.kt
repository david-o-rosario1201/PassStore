package edu.ucne.passstore.presentation.home

import edu.ucne.passstore.data.local.entities.CuentaEntity
import edu.ucne.passstore.data.local.entities.SubcuentaEntity

data class HomeUiState(
    val nombre: String? = "",
    val errorNombre: String? = "",
    val subcuentas: List<SubcuentaEntity> = emptyList(),
    val cuentas: List<CuentaEntity> = emptyList(),
)
