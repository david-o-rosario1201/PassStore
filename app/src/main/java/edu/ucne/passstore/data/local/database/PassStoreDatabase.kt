package edu.ucne.passstore.data.local.database

import androidx.room.Database
import edu.ucne.passstore.data.local.dao.CodigoSeguridadDao
import edu.ucne.passstore.data.local.dao.CuentaDao
import edu.ucne.passstore.data.local.dao.SubcuentaDao
import edu.ucne.passstore.data.local.dao.UsuarioDao
import edu.ucne.passstore.data.local.entities.CodigoSeguridadEntity
import edu.ucne.passstore.data.local.entities.CuentaEntity
import edu.ucne.passstore.data.local.entities.SubcuentaEntity
import edu.ucne.passstore.data.local.entities.UsuarioEntity

@Database(
    entities = [
        UsuarioEntity::class,
        CodigoSeguridadEntity::class,
        CuentaEntity::class,
        SubcuentaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PassStoreDatabase {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun codigoSeguridadDao(): CodigoSeguridadDao
    abstract fun cuentaDao(): CuentaDao
    abstract fun subcuentaDao(): SubcuentaDao
}