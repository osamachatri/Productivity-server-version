package com.oussama_chatri.data.daos

import com.oussama_chatri.DatabaseFactory.db
import com.oussama_chatri.data.entities.Events
import com.oussama_chatri.data.model.Event
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class EventDao {
    fun getAllEvents(): List<Event> {
        return db.from(Events).select().map { row ->
            Event(
                id = row[Events.id],
                ownerId = row[Events.ownerId]!!,
                title = row[Events.title]!!,
                description = row[Events.description]!!,
                eventDate = row[Events.eventDate].toString(),
                location = row[Events.location]!!
            )
        }
    }

    fun addEvent(event: Event): Boolean {
        val inserted = db.insert(Events) {
            set(Events.ownerId, event.ownerId)
            set(Events.title, event.title)
            set(Events.description, event.description)
            set(Events.eventDate, event.eventDate)
            set(Events.location, event.location)
        }
        return inserted > 0
    }
}
