package com.example.beer2beer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.beer2beer.R
import com.example.beer2beer.database.entities.*
import java.util.concurrent.Executors

@Database(
    entities = [Equipment::class, Ingredient::class, Recipe::class, RecipeHasIngredient::class,
        RecipeInstance::class, RecipeInstanceUseEquipment::class],
    version = 1,
    exportSchema = true
)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
    abstract fun equipmentDao(): EquipmentDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "BeerDatabase"
            )
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val dao = getInstance(context).ingredientDao()
                        // insert the data on the IO Thread
                        Executors.newSingleThreadExecutor().execute {

                            populateIngredients(dao)

                        }
                    }

                    fun populateIngredients(dao: IngredientDao?) {
                        dao?.insert(Ingredient(context.resources.getString(R.string.water), "L", 0.0))
                        dao?.insert(Ingredient(context.resources.getString(R.string.malts), "g", 0.0))
                        dao?.insert(Ingredient(context.resources.getString(R.string.hops), "g", 0.0))
                        dao?.insert(Ingredient(context.resources.getString(R.string.yeasts), "g", 0.0))
                        dao?.insert(Ingredient(context.resources.getString(R.string.sugar), "g", 0.0))
                        dao?.insert(Ingredient(context.resources.getString(R.string.additives), "g", 0.0))
                    }
                })
                .build()
    }
}