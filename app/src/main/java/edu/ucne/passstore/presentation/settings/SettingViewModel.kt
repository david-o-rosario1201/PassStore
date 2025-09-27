package edu.ucne.passstore.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.passstore.presentation.preferences.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
): ViewModel(){
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    init {
        themeApp()
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

}