package edu.ucne.passstore.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.passstore.data.local.entities.CodigoSeguridadEntity

@Dao
interface CodigoSeguridadDao {
    @Upsert
    suspend fun addCodigoSeguridad(codigoSeguridad: CodigoSeguridadEntity)

    @Query("""
        SELECT * FROM CodigoSeguridad
        WHERE codigoSeguridad = :codigoSeguridadId
        LIMIT 1
    """)
    suspend fun getCodigoSeguridad(codigoSeguridadId: Int): CodigoSeguridadEntity?
}