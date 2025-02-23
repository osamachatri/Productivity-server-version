package com.oussama_chatri.data.daos

import com.oussama_chatri.Api.comman.EventsRequestAndResponse
import com.oussama_chatri.data.entities.Events
import org.ktorm.database.Database
import org.ktorm.dsl.*

class EventDao(private val database: Database) {
    fun getAllEvents(ownerId : String): List<EventsRequestAndResponse> {
        return database.from(Events).select()
            .where { Events.ownerId eq ownerId }
            .map { row -> EventsRequestAndResponse(
                row[Events.priority]!!,
                row[Events.type]!!,
                row[Events.title]!!,
                row[Events.description],
                row[Events.startTime]!!,
                row[Events.endTime]
            ) }
    }

    fun createEvent(ownerId: String, requestAndResponse: EventsRequestAndResponse) : Int {
        return database.insert(Events) {
            set(it.ownerId, ownerId)
            set(it.priority, requestAndResponse.priority)
            set(it.type, requestAndResponse.type)
            set(it.title, requestAndResponse.title)
            set(it.description, requestAndResponse.description)
            set(it.startTime, requestAndResponse.startTime)
            set(it.endTime, requestAndResponse.endTime)
        }
    }

    fun updateEvent(
        eventId: Int, ownerId: String,
        request: EventsRequestAndResponse
    ): Boolean {
        val affectedRows = database.update(Events) {
            set(it.priority, request.priority)
            set(it.type, request.type)
            set(it.title, request.title)
            set(it.description, request.description)
            set(it.startTime, request.startTime)
            set(it.endTime, request.endTime)
            where { (Events.id eq eventId) and (Events.ownerId eq ownerId) }
        }
        return affectedRows > 0
    }

    fun deleteEvent(eventId: Int, ownerId: String): Boolean {
        val affectedRows = database.delete(Events) {
            (Events.id eq eventId) and (Events.ownerId eq ownerId)
        }
        return affectedRows > 0
    }
}
