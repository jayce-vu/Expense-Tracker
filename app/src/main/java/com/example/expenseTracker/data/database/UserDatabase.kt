package com.example.expenseTracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expenseTracker.data.database.dao.ExampleDAO
import com.example.expenseTracker.data.database.entities.ExampleEntity
import com.example.expenseTracker.utils.Constants.Companion.DATABASE_NAME

@Database(
    entities = [
        ExampleEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun exampleDao(): ExampleDAO

    companion object {
        @Volatile
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context): UserDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    DATABASE_NAME,
                ).fallbackToDestructiveMigration()
                .build()
    }
}
