package edu.ucne.passstore.presentation.subcuenta

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.passstore.data.local.entities.SubcuentaEntity
import edu.ucne.passstore.data.repository.CuentaRepository
import edu.ucne.passstore.data.repository.SubcuentaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubcuentaViewModel @Inject constructor(
    private val subcuentaRepository: SubcuentaRepository,
    private val cuentaRepository: CuentaRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(SubcuentaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCuentas()
    }

    private fun getCuentas(){
        viewModelScope.launch {
            try {
                cuentaRepository.getCuentas().collect{cuentas ->
                    _uiState.update {
                        it.copy(cuentas = cuentas)
                    }
                }
            } catch (e: Exception){
                Log.e("ViewModel", "Error obteniendo cuentas: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    fun onEvent(event: SubcuentaUiEvent){
        when(event){
            is SubcuentaUiEvent.NombreChanged -> {
                _uiState.update {
                    it.copy(nombreUsuario = event.nombre)
                }
            }
            is SubcuentaUiEvent.PasswordChanged -> {
                _uiState.update {
                    it.copy(password = event.password)
                }
            }
            is SubcuentaUiEvent.CuentaIdChanged -> {
                _uiState.update {
                    it.copy(cuentaId = event.cuentaId)
                }
            }
            SubcuentaUiEvent.Save -> {
                viewModelScope.launch {
                    if(_uiState.value.nombreUsuario.isEmpty()
                        || _uiState.value.password.isEmpty()
                        || _uiState.value.cuentaId == 0){

                        _uiState.update {
                            it.copy(errorMessage = true)
                        }
                    } else{
                        _uiState.update {
                            it.copy(
                                errorMessage = false,
                                success = true
                            )
                        }
                        subcuentaRepository.addSubcuenta(_uiState.value.toSubcuentaEntity())
                    }
                }
            }
            is SubcuentaUiEvent.CuentaIdSelected -> {
                viewModelScope.launch {
                    try {
                        val cuentaSelected = cuentaRepository.getCuenta(event.cuentaId)
                        subcuentaRepository.getSubcuentasByCuentaId(event.cuentaId).collect{ subcuentas ->
                            _uiState.update {
                                it.copy(
                                    cuenta = cuentaSelected,
                                    subcuentas = subcuentas
                                )
                            }
                        }
                    } catch (e: Exception){
                        Log.e("ViewModel", "Error obteniendo cuentas: ${e.message}", e)
                        e.printStackTrace()
                    }
                }
            }
            SubcuentaUiEvent.Delete -> {
                viewModelScope.launch {
                    subcuentaRepository.deleteSubcuenta(_uiState.value.toSubcuentaEntity())
                }
            }
            is SubcuentaUiEvent.SubcuentaIdSelected -> {
                viewModelScope.launch {
                    val subcuenta = subcuentaRepository.getSubcuenta(event.subcuentaId)
                    _uiState.update {
                        it.copy(
                            subcuentaId = subcuenta?.subcuentaId,
                            nombreUsuario = subcuenta?.nombreUsuario ?: "",
                            password = subcuenta?.password ?: "",
                            cuentaId = subcuenta?.cuentaId ?: 0
                        )
                    }
                }
            }
            is SubcuentaUiEvent.ShowEditModal -> {
                _uiState.update {
                    it.copy(showEditModal = event.showEdit)
                }
            }
            SubcuentaUiEvent.ErrorDismiss -> {
                _uiState.update {
                    it.copy(errorMessage = false)
                }
            }
        }
    }
}

fun SubcuentaUiState.toSubcuentaEntity() = SubcuentaEntity(
    subcuentaId = subcuentaId,
    nombreUsuario = nombreUsuario,
    password = password,
    cuentaId = cuentaId
)