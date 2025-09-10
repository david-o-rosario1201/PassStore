package edu.ucne.passstore.data.repository

import edu.ucne.passstore.data.local.dao.CuentaDao
import edu.ucne.passstore.data.local.entities.CuentaEntity
import javax.inject.Inject

class CuentaRepository @Inject constructor(
    private val cuentaDao: CuentaDao
) {
    suspend fun addCuenta(cuenta: CuentaEntity) = cuentaDao.addCuenta(cuenta)
    suspend fun getCuenta(cuentaId: Int) = cuentaDao.getCuenta(cuentaId)
    fun getCuentas() = cuentaDao.getCuentas()
}