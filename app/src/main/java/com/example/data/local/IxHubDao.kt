package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IxHubDao {
    // Users / Students
    @Query("SELECT * FROM users ORDER BY absenNo ASC, id ASC")
    fun observeUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users ORDER BY absenNo ASC, id ASC")
    suspend fun getAllUsersOnce(): List<UserEntity>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("SELECT COUNT(*) FROM users WHERE role = 'ADMIN'")
    suspend fun getAdminCount(): Int

    @Query("SELECT * FROM users WHERE LOWER(username) = LOWER(:username) LIMIT 1")
    suspend fun findUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)

    @Query("DELETE FROM users")
    suspend fun clearAllUsers()

    // Cash Transactions
    @Query("SELECT * FROM cash_transactions ORDER BY id DESC")
    fun observeTransactions(): Flow<List<CashTransactionEntity>>

    @Query("SELECT * FROM cash_transactions ORDER BY id ASC")
    suspend fun getAllTransactionsOnce(): List<CashTransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(items: List<CashTransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(item: CashTransactionEntity)

    @Query("DELETE FROM cash_transactions WHERE id = :id")
    suspend fun deleteTransaction(id: Int)

    @Query("DELETE FROM cash_transactions")
    suspend fun clearAllTransactions()

    // Schedules (Monday - Saturday)
    @Query("SELECT * FROM schedules ORDER BY id ASC")
    fun observeSchedules(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules ORDER BY id ASC")
    suspend fun getAllSchedulesOnce(): List<ScheduleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(items: List<ScheduleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(item: ScheduleEntity)

    @Update
    suspend fun updateSchedule(item: ScheduleEntity)

    @Query("DELETE FROM schedules WHERE id = :id")
    suspend fun deleteSchedule(id: Int)

    @Query("DELETE FROM schedules")
    suspend fun clearAllSchedules()

    // Assignments / Homework
    @Query("SELECT * FROM assignments ORDER BY isCompleted ASC, daysRemaining ASC")
    fun observeAssignments(): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignments ORDER BY id ASC")
    suspend fun getAllAssignmentsOnce(): List<AssignmentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignments(items: List<AssignmentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(item: AssignmentEntity)

    @Update
    suspend fun updateAssignment(item: AssignmentEntity)

    @Query("DELETE FROM assignments WHERE id = :id")
    suspend fun deleteAssignment(id: Int)

    @Query("DELETE FROM assignments")
    suspend fun clearAllAssignments()

    // Announcements
    @Query("SELECT * FROM announcements ORDER BY isPinned DESC, id DESC")
    fun observeAnnouncements(): Flow<List<AnnouncementEntity>>

    @Query("SELECT * FROM announcements ORDER BY id ASC")
    suspend fun getAllAnnouncementsOnce(): List<AnnouncementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncements(items: List<AnnouncementEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(item: AnnouncementEntity)

    @Update
    suspend fun updateAnnouncement(item: AnnouncementEntity)

    @Query("DELETE FROM announcements WHERE id = :id")
    suspend fun deleteAnnouncement(id: Int)

    @Query("DELETE FROM announcements")
    suspend fun clearAllAnnouncements()

    // Notifications
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    fun observeNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notifications ORDER BY id ASC")
    suspend fun getAllNotificationsOnce(): List<NotificationEntity>

    @Query("SELECT * FROM notifications WHERE eventKey = :eventKey LIMIT 1")
    suspend fun findNotificationByEventKey(eventKey: String): NotificationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(items: List<NotificationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(item: NotificationEntity)

    @Query("UPDATE notifications SET isUnread = 0")
    suspend fun markAllNotificationsRead()

    @Query("UPDATE notifications SET isUnread = 0 WHERE id = :id")
    suspend fun markNotificationRead(id: Int)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun deleteNotification(id: Int)

    @Query("DELETE FROM notifications WHERE isUnread = 0")
    suspend fun deleteReadNotifications()

    @Query("DELETE FROM notifications")
    suspend fun clearAllNotifications()

    // Activity Logs (Permanent Audit Log)
    @Query("SELECT * FROM activity_logs ORDER BY id DESC")
    fun observeActivityLogs(): Flow<List<ActivityLogEntity>>

    @Query("SELECT * FROM activity_logs ORDER BY id ASC")
    suspend fun getAllActivityLogsOnce(): List<ActivityLogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLogs(items: List<ActivityLogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(item: ActivityLogEntity)

    @Query("DELETE FROM activity_logs")
    suspend fun clearAllActivityLogs()

    // Bot Templates
    @Query("SELECT * FROM bot_templates")
    fun observeBotTemplates(): Flow<List<BotTemplateEntity>>

    @Query("SELECT * FROM bot_templates ORDER BY id ASC")
    suspend fun getAllBotTemplatesOnce(): List<BotTemplateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBotTemplates(items: List<BotTemplateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBotTemplate(item: BotTemplateEntity)

    @Query("DELETE FROM bot_templates")
    suspend fun clearAllBotTemplates()

    // Bot Dispatch Logs
    @Query("SELECT * FROM bot_dispatch_logs ORDER BY id DESC")
    fun observeBotDispatchLogs(): Flow<List<BotDispatchLogEntity>>

    @Query("SELECT * FROM bot_dispatch_logs ORDER BY id ASC")
    suspend fun getAllBotDispatchLogsOnce(): List<BotDispatchLogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBotDispatchLogs(items: List<BotDispatchLogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBotDispatchLog(item: BotDispatchLogEntity)

    @Update
    suspend fun updateBotDispatchLog(item: BotDispatchLogEntity)

    @Query("DELETE FROM bot_dispatch_logs")
    suspend fun clearAllBotDispatchLogs()

    // Offline-First Sync Queue
    @Query("SELECT * FROM sync_queue ORDER BY id DESC")
    fun observeSyncQueue(): Flow<List<SyncQueueEntity>>

    @Query("SELECT * FROM sync_queue ORDER BY id ASC")
    suspend fun getAllSyncQueueOnce(): List<SyncQueueEntity>

    @Query("SELECT COUNT(*) FROM sync_queue WHERE status = 'PENDING' OR status = 'FAILED'")
    suspend fun getPendingSyncCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncQueueItem(item: SyncQueueEntity)

    @Query("UPDATE sync_queue SET status = 'SYNCED', lastError = '', updatedAt = :timestamp WHERE status = 'PENDING' OR status = 'FAILED'")
    suspend fun markAllSyncQueueSynced(timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE sync_queue SET status = 'FAILED', lastError = :errorMsg, updatedAt = :timestamp WHERE status = 'PENDING'")
    suspend fun markPendingSyncQueueFailed(errorMsg: String, timestamp: Long = System.currentTimeMillis())

    @Query("DELETE FROM sync_queue WHERE status = 'SYNCED'")
    suspend fun clearSyncedQueueItems()

    @Query("DELETE FROM sync_queue")
    suspend fun clearAllSyncQueue()
}
