package edu.ucne.passstore.presentation.subcuenta

import edu.ucne.passstore.data.local.entities.CuentaEntity
import edu.ucne.passstore.data.local.entities.SubcuentaEntity

data class SubcuentaUiState(
    val subcuentaId: Int? = null,
    val nombreUsuario: String = "",
    val password: String = "",
    val cuentaId: Int = 0,
    val cuentas: List<CuentaEntity> = emptyList(),
    val subcuentas: List<SubcuentaEntity> = emptyList(),
    val errorMessage: String = "",
    val success: Boolean = false
)