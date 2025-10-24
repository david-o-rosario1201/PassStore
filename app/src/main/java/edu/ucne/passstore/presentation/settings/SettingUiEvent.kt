package edu.ucne.passstore.presentation.settings

import androidx.compose.runtime.snapshots.SnapshotStateList

sealed interface SettingUiEvent {
    data class CheckSingleCodeIsNotEmpty(val pinCode: SnapshotStateList<String>): SettingUiEvent
    data class CheckCodesAreNotEmpty(val pinNewCode: SnapshotStateList<String>, val pinConfirmCode: SnapshotStateList<String>): SettingUiEvent
    data class CheckCodesAreEquals(val pinNewCode: SnapshotStateList<String>, val pinConfirmCode: SnapshotStateList<String>): SettingUiEvent
    data class CheckIsCorrect(val pinCode: SnapshotStateList<String>): SettingUiEvent
    data class ShowSecurityCode(val showSecurityCode: Boolean): SettingUiEvent
    data class ShowSuccessModal(val showModal: Boolean): SettingUiEvent
    data object Save: SettingUiEvent
    data object ResetErrorMessages: SettingUiEvent
    data object DismissAllModals: SettingUiEvent
}