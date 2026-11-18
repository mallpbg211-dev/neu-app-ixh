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
        BotDispatchLogEntity::class,
        SyncQueueEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class IxHubDatabase : RoomDatabase() {
    abstract fun dao(): IxHubDao

    companion object {
        const val DATABASE_NAME = "ixh_hub_spensari_v2_clean.db"
        const val SCHEMA_VERSION = 3

        @Volatile
        private var INSTANCE: IxHubDatabase? = null

        fun getInstance(context: Context): IxHubDatabase {
            return INSTANCE ?: synchronized(this) {
                // Delete legacy v1 database file on device if present so only clean DB remains active
                context.applicationContext.deleteDatabase("ixh_hub_spensari.db")

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IxHubDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration(dropAllTables = true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
