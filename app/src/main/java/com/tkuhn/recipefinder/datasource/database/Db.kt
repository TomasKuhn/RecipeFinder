package com.tkuhn.recipefinder.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tkuhn.recipefinder.App
import com.tkuhn.recipefinder.datasource.database.converter.DbConverters
import com.tkuhn.recipefinder.datasource.database.dao.RecipesDao
import com.tkuhn.recipefinder.datasource.database.dto.DbRecipeDetail
import com.tkuhn.recipefinder.datasource.database.dto.DbRecipeSummary

@Database(
    entities = [
        DbRecipeDetail::class,
        DbRecipeSummary::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DbConverters::class)
abstract class Db : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

    companion object {

        private var sInstance: Db? = null

        @Synchronized
        fun getInstance(context: Context = App.instance): Db {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context,
                    Db::class.java,
                    "ex.db"
                )
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return sInstance!!
        }
    }
}