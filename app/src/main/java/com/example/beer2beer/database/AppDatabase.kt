package com.example.beer2beer.database

import androidx.room.*
import com.example.beer2beer.database.entities.*

@Database(
    entities = [Equipment::class, Ingredient::class, Recipe::class, RecipeHasIngredient::class,
        RecipeInstance::class, RecipeInstanceUseEquipment::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao (): IngredientDao
    abstract fun equipmentDao (): EquipmentDao
    /*
    companion object {
        // Singleton Pattern applied to ensure that only one instance of the database is created.
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "BeerDatabase"
            )
                .build()
    }

     */
}