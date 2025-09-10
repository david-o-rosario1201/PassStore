package edu.ucne.passstore.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.ucne.passstore.data.local.entities.CuentaEntity

fun loadCuentaDesdeJson(context: Context): List<CuentaEntity> {
    return try {
        val jsonString = context.assets.open("cuentas_iniciales.json")
            .bufferedReader()
            .use { it.readText() }

        val gson = Gson()
        val listType = object : TypeToken<List<CuentaJson>>() {}.type
        val cuentasJson: List<CuentaJson> = gson.fromJson(jsonString, listType)

        cuentasJson.mapNotNull {
            if (it.iconoResId.isBlank()) return@mapNotNull null
            CuentaEntity(nombre = it.nombre, iconoResName = it.iconoResId)
        }
    } catch (e: Exception) {
        Log.e("LOAD_JSON", "Error leyendo JSON", e)
        emptyList()
    }
}
