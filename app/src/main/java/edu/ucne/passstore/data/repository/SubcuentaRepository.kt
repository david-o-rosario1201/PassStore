package edu.ucne.passstore.data.repository

import edu.ucne.passstore.data.local.dao.SubcuentaDao
import edu.ucne.passstore.data.local.entities.SubcuentaEntity
import javax.inject.Inject

class SubcuentaRepository @Inject constructor(
    private val subcuentaDao: SubcuentaDao
) {
    suspend fun addSubcuenta(subcuenta: SubcuentaEntity) = subcuentaDao.addSubcuenta(subcuenta)
    suspend fun getSubcuenta(subcuentaId: Int) = subcuentaDao.getSubcuenta(subcuentaId)
    suspend fun deleteSubcuenta(subcuenta: SubcuentaEntity) = subcuentaDao.deleteSubcuenta(subcuenta)
    fun getSubcuentas() = subcuentaDao.getSubcuentas()
}