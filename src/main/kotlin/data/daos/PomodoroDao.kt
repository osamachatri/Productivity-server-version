package com.oussama_chatri.data.daos
import com.oussama_chatri.DatabaseFactory.db
import com.oussama_chatri.data.model.Pomodoro
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class PomodoroDao {
    fun getAllPomodoroSessions(): List<Pomodoro> {
        return db.from(com.oussama_chatri.data.entities.Pomodoro).select().map { row ->
            Pomodoro(
                id = row[com.oussama_chatri.data.entities.Pomodoro.id],
                ownerId = row[com.oussama_chatri.data.entities.Pomodoro.ownerId]!!,
                sessionCount = row[com.oussama_chatri.data.entities.Pomodoro.sessionCount]!!,
                focusTime = row[com.oussama_chatri.data.entities.Pomodoro.focusTime]!!,
                breakTime = row[com.oussama_chatri.data.entities.Pomodoro.breakTime]!!
            )
        }
    }

    fun addPomodoro(pomodoro: Pomodoro): Boolean {
        val inserted = db.insert(com.oussama_chatri.data.entities.Pomodoro) {
            set(com.oussama_chatri.data.entities.Pomodoro.ownerId, pomodoro.ownerId)
            set(com.oussama_chatri.data.entities.Pomodoro.sessionCount, pomodoro.sessionCount)
            set(com.oussama_chatri.data.entities.Pomodoro.focusTime, pomodoro.focusTime)
            set(com.oussama_chatri.data.entities.Pomodoro.breakTime, pomodoro.breakTime)
        }
        return inserted > 0
    }
}
