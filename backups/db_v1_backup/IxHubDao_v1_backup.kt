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
    @Query("SELECT * FROM users ORDER BY absenNo ASC")
    fun observeUsers(): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)

    // Cash Transactions
    @Query("SELECT * FROM cash_transactions ORDER BY id DESC")
    fun observeTransactions(): Flow<List<CashTransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(items: List<CashTransactionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(item: CashTransactionEntity)

    // Schedules (Monday - Saturday)
    @Query("SELECT * FROM schedules ORDER BY id ASC")
    fun observeSchedules(): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(items: List<ScheduleEntity>)

    @Update
    suspend fun updateSchedule(item: ScheduleEntity)

    // Assignments / Homework
    @Query("SELECT * FROM assignments ORDER BY isCompleted ASC, daysRemaining ASC")
    fun observeAssignments(): Flow<List<AssignmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignments(items: List<AssignmentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(item: AssignmentEntity)

    @Update
    suspend fun updateAssignment(item: AssignmentEntity)

    @Query("DELETE FROM assignments WHERE id = :id")
    suspend fun deleteAssignment(id: Int)

    // Announcements
    @Query("SELECT * FROM announcements ORDER BY isPinned DESC, id DESC")
    fun observeAnnouncements(): Flow<List<AnnouncementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncements(items: List<AnnouncementEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(item: AnnouncementEntity)

    @Update
    suspend fun updateAnnouncement(item: AnnouncementEntity)

    @Query("DELETE FROM announcements WHERE id = :id")
    suspend fun deleteAnnouncement(id: Int)

    // Notifications
    @Query("SELECT * FROM notifications ORDER BY id ASC")
    fun observeNotifications(): Flow<List<NotificationEntity>>

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

    // Activity Logs (Permanent Audit Log - Storyboard 26)
    @Query("SELECT * FROM activity_logs ORDER BY id DESC")
    fun observeActivityLogs(): Flow<List<ActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLogs(items: List<ActivityLogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(item: ActivityLogEntity)

    // Bot Templates (Storyboard 22)
    @Query("SELECT * FROM bot_templates")
    fun observeBotTemplates(): Flow<List<BotTemplateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBotTemplates(items: List<BotTemplateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBotTemplate(item: BotTemplateEntity)

    // Bot Dispatch Logs (Storyboard 06)
    @Query("SELECT * FROM bot_dispatch_logs ORDER BY id DESC")
    fun observeBotDispatchLogs(): Flow<List<BotDispatchLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBotDispatchLogs(items: List<BotDispatchLogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBotDispatchLog(item: BotDispatchLogEntity)

    @Update
    suspend fun updateBotDispatchLog(item: BotDispatchLogEntity)
}
