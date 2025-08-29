package edu.ucne.passstore.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.passstore.data.local.entities.CuentaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CuentaDao {
    @Upsert
    suspend fun addCuenta(cuenta: CuentaEntity)

    @Query("""
        SELECT * FROM Cuentas
        WHERE cuentaId = :cuentaId
        LIMIT 1
    """)
    suspend fun getCuenta(cuentaId: Int): CuentaEntity?

    @Query("SELECT * FROM Cuentas")
    fun getCuentas(): Flow<List<CuentaEntity>>
}