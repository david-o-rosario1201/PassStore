package edu.ucne.passstore.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.passstore.data.local.database.PassStoreDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providesPassStoreDatabase(@ApplicationContext appContext: Context): PassStoreDatabase =
        Room.databaseBuilder(
            appContext,
            PassStoreDatabase::class.java,
            "PassStore.db"
        ).fallbackToDestructiveMigration().build()

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