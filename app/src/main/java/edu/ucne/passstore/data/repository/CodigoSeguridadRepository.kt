package edu.ucne.passstore.data.repository

import edu.ucne.passstore.data.local.dao.CodigoSeguridadDao
import edu.ucne.passstore.data.local.entities.CodigoSeguridadEntity
import javax.inject.Inject

class CodigoSeguridadRepository @Inject constructor(
    private val codigoSeguridadDao: CodigoSeguridadDao
) {
    suspend fun addCodigoSeguridad(codigoSeguridad: CodigoSeguridadEntity) = codigoSeguridadDao.addCodigoSeguridad(codigoSeguridad)
    suspend fun getCodigoSeguridad(codigoSeguridadId: Int) = codigoSeguridadDao.getCodigoSeguridad(codigoSeguridadId)
}