package edu.ucne.passstore.presentation.settings

data class SettingUiState(
    val pinCode: String = "",
    val showSecurityCode: Boolean = false,
    val showNewSecurityCode: Boolean = false,
    val showConfirmSecurityCode: Boolean = false,
    val checkCodeIsNotEmpty: Boolean = false,
    val checkCodeIsIncorrect: Boolean = false,
    val showCodesAreNotEqualsMessage: Boolean = false,
    val showEmptyMessage: Boolean = false,
    val showIncompleteMessage:Boolean = false,
    val showSuccessModal: Boolean = false,
    val errorState: Boolean = false
)
