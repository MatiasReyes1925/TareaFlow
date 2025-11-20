package com.example.tareaflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tareaflow.model.Usuario
import com.example.tareaflow.model.UsuarioDao
import com.example.tareaflow.model.Tarea
import com.example.tareaflow.model.TareaDao

@Database(entities = [Usuario::class, Tarea::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun tareaDao(): TareaDao

    companion object {
        @Volatile private var instancia: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tarea_flow_db"
                ).fallbackToDestructiveMigration()
                    .build().also { instancia = it }
            }
        }
    }
}
