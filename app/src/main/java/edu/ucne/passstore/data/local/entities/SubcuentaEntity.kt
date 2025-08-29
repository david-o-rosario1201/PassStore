package edu.ucne.passstore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subcuentas")
data class SubcuentaEntity(
    @PrimaryKey
    val subcuentaId: Int? = null,
    val nombreUsuario: String,
    val password: String,
    val cuentaId: Int
)
