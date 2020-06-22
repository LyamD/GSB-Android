package fr.durandlyam.gsb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Visite::class], version = 1, exportSchema = false )
abstract  class GSBDatabase : RoomDatabase(){

    abstract val visiteDao: VisiteDao

    companion object {
        @Volatile
        private var INSTANCE: GSBDatabase? = null

        fun getInstance(context: Context) : fr.durandlyam.gsb.database.GSBDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GSBDatabase::class.java,
                        "gsb_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance;
            }
        }
    }
}