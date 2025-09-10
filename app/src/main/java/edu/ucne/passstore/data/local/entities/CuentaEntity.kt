package edu.ucne.passstore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cuentas")
data class CuentaEntity(
    @PrimaryKey
    val cuentaId: Int? = null,
    val nombre: String,
    val iconoResName: String
)
