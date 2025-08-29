package edu.ucne.passstore.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CodigoSeguridad")
data class CodigoSeguridadEntity(
    @PrimaryKey
    val codigoSeguridadId: Int? = null,
    val codigoSeguridad: String,
    val mostrar: Boolean = true
)
