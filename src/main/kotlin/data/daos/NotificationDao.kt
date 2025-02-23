package com.oussama_chatri.data.daos

import com.oussama_chatri.Api.comman.NotificationRequestAndResponse
import com.oussama_chatri.data.entities.Notifications
import org.ktorm.database.Database
import org.ktorm.dsl.*

class NotificationDao(private val database: Database) {

    fun getAllNotifications(ownerId : String): List<NotificationRequestAndResponse> {
        return database.from(Notifications).select()
            .where { Notifications.ownerId eq ownerId }
            .map { row -> NotificationRequestAndResponse(
                row[Notifications.title]!!,
                row[Notifications.content]!!,
                row[Notifications.creationTime]!!,
                row[Notifications.priority]!!,
                ) }
    }

    fun createNotification(ownerId: String, request: NotificationRequestAndResponse) : Int {
        return database.insert(Notifications) {
            set(it.ownerId, ownerId)
            set(it.title, request.title)
            set(it.content, request.content)
            set(it.creationTime, request.creationTime)
            set(it.priority, request.priority)
        }
    }

    fun updateNotification(
        notificationId: Int, ownerId: String,
        request: NotificationRequestAndResponse
    ): Boolean {
        val affectedRows = database.update(Notifications) {
            set(it.title, request.title)
            set(it.content, request.content)
            set(it.creationTime, request.creationTime)
            set(it.priority, request.priority)
            where { (Notifications.id eq notificationId) and (Notifications.ownerId eq ownerId) }
        }
        return affectedRows > 0
    }

    fun deleteNotification(notificationId: Int, ownerId: String): Boolean {
        val affectedRows = database.delete(Notifications) {
            (Notifications.id eq notificationId) and (Notifications.ownerId eq ownerId)
        }
        return affectedRows > 0
    }
}
