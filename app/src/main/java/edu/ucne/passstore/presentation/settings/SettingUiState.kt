package edu.ucne.passstore.presentation.settings

data class SettingUiState(
    val pinCode: String = "",
    val userName: String = "",
    val userDateRegister: String = "",
    val biometricAuth: Boolean = false,
    val requestBiometricAuth: Boolean = false,
    val showSecurityCode: Boolean = false,
    val showNewSecurityCode: Boolean = false,
    val showUserView: Boolean = false,
    val showConfirmModal: Boolean = false,
    val checkCodeIsNotEmpty: Boolean = false,
    val checkCodeIsIncorrect: Boolean = false,
    val showCodesAreNotEqualsMessage: Boolean = false,
    val showEmptyMessage: Boolean = false,
    val showIncompleteMessage:Boolean = false,
    val showSuccessModal: Boolean = false,
    val showBiometricModal: Boolean = false,
    val showBiometricAuthDisabledModal: Boolean = false,
    val shouldHighlightBiometric: Boolean = false,
    val errorState: Boolean = false,
    val codeSucceeded: Boolean = false
)
