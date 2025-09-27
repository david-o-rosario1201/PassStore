package edu.ucne.passstore.presentation.subcuenta

sealed interface SubcuentaUiEvent {
    data class NombreChanged(val nombre: String): SubcuentaUiEvent
    data class PasswordChanged(val password: String): SubcuentaUiEvent
    data class CuentaIdChanged(val cuentaId: Int): SubcuentaUiEvent
    data object Save: SubcuentaUiEvent
    data object Delete: SubcuentaUiEvent
    data object ErrorDismiss: SubcuentaUiEvent
}