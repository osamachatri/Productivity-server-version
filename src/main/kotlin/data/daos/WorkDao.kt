package com.oussama_chatri.data.daos

import com.oussama_chatri.DatabaseFactory.db
import com.oussama_chatri.data.entities.Works
import com.oussama_chatri.data.model.Work
import org.ktorm.dsl.*
import org.ktorm.entity.*

class WorkDao {
    fun getAllWorkEntries(): List<Work> {
        return db.from(Works).select().map { row ->
            Work(
                id = row[Works.id],
                ownerId = row[Works.ownerId]!!,
                title = row[Works.title]!!,
                description = row[Works.description]!!,
                startTime = row[Works.startTime].toString(),
                endTime = row[Works.endTime].toString()
            )
        }
    }

    fun addWork(work: Work): Boolean {
        val inserted = db.insert(Works) {
            set(Works.ownerId, work.ownerId)
            set(Works.title, work.title)
            set(Works.description, work.description)
            set(Works.startTime, work.startTime)
            set(Works.endTime, work.endTime)
        }
        return inserted > 0
    }
}
