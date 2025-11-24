package edu.ucne.passstore.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.passstore.presentation.preferences.SettingPreferences
import edu.ucne.passstore.presentation.preferences.ThemePreferences
import edu.ucne.passstore.presentation.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val themePreferences: ThemePreferences,
    private val settingPreferences: SettingPreferences,
    private val userPreferences: UserPreferences
): ViewModel(){
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    init{
        getUserInfo()
    }

    private fun getUserInfo(){
        viewModelScope.launch {
            val userInfo = userPreferences.getUserInfo()
            _uiState.update {
                it.copy(
                    userName = userInfo.userName,
                    userDateRegister = userInfo.userDateRegister
                )
            }
        }
    }

    private fun themeApp(){
        viewModelScope.launch {
            themePreferences.isDarkModeFlow.collectLatest { darkMode ->
                _isDarkMode.value = darkMode
            }
        }
    }

    fun toggleDarkMode(){
        viewModelScope.launch {
            val newMode = !_isDarkMode.value
            themePreferences.setDarkMode(newMode)
        }
    }

    fun onEvent(event: SettingUiEvent){
        when(event){
            is SettingUiEvent.CheckSingleCodeIsNotEmpty -> {
                val joinedCode = event.pinCode.joinToString("")
                val hasAnyInput = joinedCode.isNotEmpty()
                val allFilled = event.pinCode.all { it.isNotEmpty() }

                when{
                    !hasAnyInput -> { _uiState.update { it.copy(showEmptyMessage = true, errorState = true) } }
                    !allFilled -> { _uiState.update { it.copy(showIncompleteMessage = true, errorState = true) } }
                    else -> { _uiState.update { it.copy(checkCodeIsNotEmpty = true, errorState = false) } }
                }

            }
            is SettingUiEvent.CheckCodesAreNotEmpty ->{
                val joinedNewCode = event.pinNewCode.joinToString("")
                val joinedConfirmCode = event.pinConfirmCode.joinToString("")
                val hasAnyInput = joinedNewCode.isNotEmpty() || joinedConfirmCode.isNotEmpty()
                val allFilled =
                    event.pinNewCode.all { it.isNotEmpty() } &&
                            event.pinConfirmCode.all { it.isNotEmpty() }

                when{
                    !hasAnyInput -> { _uiState.update { it.copy(showEmptyMessage = true, errorState = true) } }
                    !allFilled -> {_uiState.update { it.copy(showIncompleteMessage = true, errorState = true) }}
                    else -> { _uiState.update { it.copy(checkCodeIsNotEmpty = true, errorState = false) }}
                }
            }
            is SettingUiEvent.CheckIsCorrect -> {
                viewModelScope.launch {
                    val code = event.pinCode.joinToString("")
                    val codeSaved = settingPreferences.getPinCode()

                    if(code != codeSaved){
                        _uiState.update {
                            it.copy(checkCodeIsIncorrect = true, errorState = true)
                        }
                    } else{
                        _uiState.update {
                            it.copy(
                                errorState = false,
                                showSecurityCode = false,
                                //Como no voy a abrirlo en el home, enviare a mostrar la subcuenta
                                codeSucceeded = true,
                                showNewSecurityCode = true
                            )
                        }
                    }
                }
            }
            is SettingUiEvent.CheckCodesAreEquals -> {
                val newCode = event.pinNewCode.joinToString("")
                val confirmCode = event.pinConfirmCode.joinToString("")

                if(newCode != confirmCode){
                    _uiState.update {
                        it.copy(showCodesAreNotEqualsMessage = true, errorState = true)
                    }
                } else{
                    _uiState.update {
                        it.copy(
                            errorState = false,
                            showNewSecurityCode = false,
                            showConfirmModal = true,
                            pinCode = newCode
                        )
                    }
                }
            }
            is SettingUiEvent.SetPinCode -> {
                viewModelScope.launch{
                    settingPreferences.setPinCode(uiState.value.pinCode)
                    onEvent(SettingUiEvent.DismissAllModals)
                    onEvent(SettingUiEvent.ShowSuccessModal(true))
                }
            }
            is SettingUiEvent.UserNameChanged -> {
                _uiState.update {
                    it.copy(userName = event.userName)
                }
            }
            is SettingUiEvent.SetUserInfo -> {
                viewModelScope.launch {
                    userPreferences.setUserInfo(event.userName)
                    onEvent(SettingUiEvent.DismissAllModals)
                }
            }
            is SettingUiEvent.ShowSecurityCode -> {
                _uiState.update {
                    it.copy(showSecurityCode = event.showSecurityCode)
                }
            }
            is SettingUiEvent.ShowSuccessModal -> {
                _uiState.update {
                    it.copy(showSuccessModal = event.showModal)
                }
            }
            is SettingUiEvent.ShowConfirmModal -> {
                _uiState.update {
                    it.copy(showConfirmModal = event.showModal)
                }
            }
            is SettingUiEvent.ShowUserView -> {
                _uiState.update {
                    it.copy(showUserView = event.showModal)
                }
            }
            SettingUiEvent.ResetErrorMessages -> {
                _uiState.update {
                    it.copy(
                        showEmptyMessage = false,
                        showIncompleteMessage = false,
                        checkCodeIsIncorrect = false,
                        showCodesAreNotEqualsMessage = false,
                        errorState = false
                    )
                }
            }
            SettingUiEvent.DismissAllModals -> {
                _uiState.update {
                    it.copy(
                        showSecurityCode = false,
                        showNewSecurityCode = false,
                        showConfirmModal = false,
                        showUserView = false
                    )
                }
            }
        }
    }
}