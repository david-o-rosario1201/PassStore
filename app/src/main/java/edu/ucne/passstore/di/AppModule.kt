package edu.ucne.passstore.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.passstore.data.local.database.PassStoreDatabase
import edu.ucne.passstore.presentation.preferences.SettingPreferences
import edu.ucne.passstore.presentation.preferences.ThemePreferences
import edu.ucne.passstore.presentation.preferences.UserPreferences
import edu.ucne.passstore.utils.loadCuentaDesdeJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providesPassStoreDatabase(@ApplicationContext appContext: Context): PassStoreDatabase {

        lateinit var database: PassStoreDatabase

        database = Room.databaseBuilder(
            appContext,
            PassStoreDatabase::class.java,
            "PassStore.db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Cargar cuentas desde JSON
                            val cuentasIniciales = loadCuentaDesdeJson(appContext)

                            // Insertar en la BD
                            database.cuentaDao().addCuentas(cuentasIniciales)

                            Log.d("DB_DEBUG", "Cuentas insertadas: ${cuentasIniciales.size}")
                        } catch (e: Exception) {
                            Log.e("DB_ERROR", "Error insertando cuentas iniciales", e)
                        }
                    }
                }
            }).build()

        return database
    }

    @Provides
    @Singleton
    fun providesThemePreferences(@ApplicationContext context: Context): ThemePreferences {
        return ThemePreferences(context)
    }

    @Provides
    @Singleton
    fun providesUserPreferences(@ApplicationContext context: Context): UserPreferences{
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun providesSettingPreferences(@ApplicationContext context: Context): SettingPreferences{
        val settingPreferences = SettingPreferences(context)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                settingPreferences.initializeDefaultPin()
            } catch (e: Exception) {
                Log.e("DATASTORE_INIT", "Error inicializando PIN por defecto", e)
            }
        }

        return settingPreferences
    }

    @Provides
    @Singleton
    fun providesUsuarioDao(passStoreDb: PassStoreDatabase) = passStoreDb.usuarioDao()

    @Provides
    @Singleton
    fun providesCodigoSeguridadDao(passStoreDb: PassStoreDatabase) = passStoreDb.codigoSeguridadDao()

    @Provides
    @Singleton
    fun providesCuentaDao(passStoreDb: PassStoreDatabase) = passStoreDb.cuentaDao()

    @Provides
    @Singleton
    fun providesSubcuentaDao(passStoreDb: PassStoreDatabase) = passStoreDb.subcuentaDao()
}