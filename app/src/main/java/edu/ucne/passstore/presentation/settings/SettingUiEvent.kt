package edu.ucne.passstore.presentation.settings

import androidx.compose.runtime.snapshots.SnapshotStateList

sealed interface SettingUiEvent {
    data class CheckSingleCodeIsNotEmpty(val pinCode: SnapshotStateList<String>): SettingUiEvent
    data class CheckCodesAreNotEmpty(val pinNewCode: SnapshotStateList<String>, val pinConfirmCode: SnapshotStateList<String>): SettingUiEvent
    data class CheckCodesAreEquals(val pinNewCode: SnapshotStateList<String>, val pinConfirmCode: SnapshotStateList<String>): SettingUiEvent
    data class CheckIsCorrect(val pinCode: SnapshotStateList<String>, val cuentaIdSelected: Int = 0): SettingUiEvent
    data class ShowSecurityCode(val showSecurityCode: Boolean): SettingUiEvent
    data class ShowSuccessModal(val showModal: Boolean): SettingUiEvent
    data class ShowConfirmModal(val showModal: Boolean): SettingUiEvent
    data class ShowUserView(val showModal: Boolean): SettingUiEvent
    data class ShowBiometricModal(val showModal: Boolean): SettingUiEvent
    data class ShowBiometricAuthDisabledModal(val showModal: Boolean): SettingUiEvent
    data class BiometricAuthSuccess(val showModal: Boolean): SettingUiEvent
    data class SetUserInfo(val userName: String): SettingUiEvent
    data class UserNameChanged(val userName: String): SettingUiEvent
    data object SetPinCode: SettingUiEvent
    data class BiometricAuthResult(val success: Boolean): SettingUiEvent
    data class SetBiometricAuth(val enabled: Boolean): SettingUiEvent
    data object ResetErrorMessages: SettingUiEvent
    data object DismissAllModals: SettingUiEvent
    data object RequestBiometricAuth: SettingUiEvent
}