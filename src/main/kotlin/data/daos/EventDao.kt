package com.oussama_chatri.data.daos

import com.oussama_chatri.data.entities.Events
import com.oussama_chatri.data.model.Event
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class EventDao(private val database: Database) {
    fun getAllEvents(): List<Event> {
        return database.from(Events).select()
            .map { row -> Event(
                row[Events.id]!!,
                row[Events.ownerId]!!,
                row[Events.priority]!!,
                row[Events.type]!!,
                row[Events.title]!!,
                row[Events.description],
                row[Events.startTime]!!,
                row[Events.endTime]
            ) }
    }

    fun createEvent(event: Event) {
        database.insert(Events) {
            set(it.ownerId, event.ownerId)
            set(it.priority, event.priority)
            set(it.type, event.type)
            set(it.title, event.title)
            set(it.description, event.description)
            set(it.startTime, event.startTime)
            set(it.endTime, event.endTime)
        }
    }
}
