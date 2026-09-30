package com.example.spendioapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spendioapp.models.Expense

@Database(
    entities = [Expense::class],
    version = 1,
    exportSchema = false
)
abstract class SpendioDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {

        @Volatile
        private var INSTANCE: SpendioDatabase? = null

        fun getDatabase(context: Context): SpendioDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpendioDatabase::class.java,
                    "spendio_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}