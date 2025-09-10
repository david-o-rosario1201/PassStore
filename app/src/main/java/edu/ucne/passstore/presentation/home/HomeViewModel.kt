package edu.ucne.passstore.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.passstore.data.repository.CuentaRepository
import edu.ucne.passstore.data.repository.SubcuentaRepository
import edu.ucne.passstore.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val subcuentaRepository: SubcuentaRepository,
    private val cuentaRepository: CuentaRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUsuario()
        getSubcuentas()
        getCuentas()
    }

    private fun getUsuario(){
        viewModelScope.launch {
            try {
                val usuario = usuarioRepository.getUsuario(1)
                _uiState.update {
                    it.copy(nombre = usuario?.nombre ?: "No hay usuarios creados!")
                }
            } catch (e: Exception){
                Log.e("ViewModel", "Error obteniendo usuario: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    private fun getSubcuentas(){
        viewModelScope.launch {
            try{
                subcuentaRepository.getSubcuentas().collect{subcuentas ->
                    _uiState.update {
                        it.copy(subcuentas = subcuentas)
                    }
                }
            }catch(e: Exception){
                Log.e("ViewModel", "Error obteniendo subcuentas: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }

    private fun getCuentas(){
        viewModelScope.launch {
            try{
                cuentaRepository.getCuentas().collect{cuentas ->
                    _uiState.update {
                        it.copy(cuentas = cuentas)
                    }
                }
            }catch(e: Exception){
                Log.e("ViewModel", "Error obteniendo cuentas: ${e.message}", e)
                e.printStackTrace()
            }
        }
    }
}