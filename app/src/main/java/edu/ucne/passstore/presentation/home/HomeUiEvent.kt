package edu.ucne.passstore.presentation.home

sealed interface HomeUiEvent {
    data class CuentaIdSelected(val cuentaIdSelected: Int): HomeUiEvent
}