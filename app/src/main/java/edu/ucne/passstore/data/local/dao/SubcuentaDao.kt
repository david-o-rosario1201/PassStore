package edu.ucne.passstore.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.passstore.data.local.entities.SubcuentaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubcuentaDao {
    @Upsert
    suspend fun addSubcuenta(subcuenta: SubcuentaEntity)

    @Query("""
        SELECT * FROM Subcuentas
        WHERE subcuentaId = :subcuentaId
        LIMIT 1
    """)
    suspend fun getSubcuenta(subcuentaId: Int): SubcuentaEntity?

    @Delete
    suspend fun deleteSubcuenta(subcuenta: SubcuentaEntity)

    @Query("SELECT * FROM Subcuentas")
    fun getSubcuentas(): Flow<List<SubcuentaEntity>>
}