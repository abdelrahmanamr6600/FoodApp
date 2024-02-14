package com.abdelrahman.foodapp.data.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdelrahman.foodapp.data.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDataBase:RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object{
        @Volatile
        var instance: MealDataBase? = null
        @Synchronized
        fun getInstance(context: Context): MealDataBase {
            if (instance ==null){
                instance =Room.databaseBuilder(
                    context,
                    MealDataBase::class.java,
                    "Food.db"
                ).fallbackToDestructiveMigration().build()
            }
            return instance as MealDataBase
        }
    }
}