package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        CashTransactionEntity::class,
        ScheduleEntity::class,
        AssignmentEntity::class,
        AnnouncementEntity::class,
        NotificationEntity::class,
        ActivityLogEntity::class,
        BotTemplateEntity::class,
        BotDispatchLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class IxHubDatabase : RoomDatabase() {
    abstract fun dao(): IxHubDao

    companion object {
        @Volatile
        private var INSTANCE: IxHubDatabase? = null

        fun getInstance(context: Context): IxHubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IxHubDatabase::class.java,
                    "ixh_hub_spensari.db"
                ).fallbackToDestructiveMigration(dropAllTables = true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
