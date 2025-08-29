package edu.ucne.passstore.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.passstore.data.local.entities.UsuarioEntity

@Dao
interface UsuarioDao {
    @Upsert
    suspend fun addUsuario (usuario: UsuarioEntity)

    @Query("""
        SELECT * FROM Usuarios
        WHERE usuarioId = :usuarioId
        LIMIT 1
    """)
    suspend fun getUsuario (usuarioId: Int): UsuarioEntity?
}