package edu.ucne.passstore.data.repository

import edu.ucne.passstore.data.local.dao.UsuarioDao
import edu.ucne.passstore.data.local.entities.UsuarioEntity
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioDao: UsuarioDao
) {
    suspend fun addUsuario(usuario: UsuarioEntity) = usuarioDao.addUsuario(usuario)
    suspend fun getUsuario(usuarioId: Int) = usuarioDao.getUsuario(usuarioId)
}