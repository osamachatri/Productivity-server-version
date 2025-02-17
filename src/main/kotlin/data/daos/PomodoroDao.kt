package com.oussama_chatri.data.daos

import com.oussama_chatri.data.entities.Pomodoros
import com.oussama_chatri.data.model.Pomodoro
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class PomodoroDao(private val database: Database) {
    fun getAllPomodoros(): List<Pomodoro> {
        return database.from(Pomodoros).select()
            .map { row -> Pomodoro(
                row[Pomodoros.id]!!,
                row[Pomodoros.ownerId]!!,
                row[Pomodoros.priority]!!,
                row[Pomodoros.title]!!,
                row[Pomodoros.description],
                row[Pomodoros.numPomodoros]!!,
                row[Pomodoros.creationTime]!!,
                row[Pomodoros.pomodoroTime]!!,
                row[Pomodoros.shortBreakTime]!!,
                row[Pomodoros.longBreakTime]!!,
                row[Pomodoros.progress]!!
            ) }
    }

    fun createPomodoro(pomodoro: Pomodoro) {
        database.insert(Pomodoros) {
            set(it.ownerId, pomodoro.ownerId)
            set(it.priority, pomodoro.priority)
            set(it.title, pomodoro.title)
            set(it.description, pomodoro.description)
            set(it.numPomodoros, pomodoro.numPomodoros)
            set(it.creationTime, pomodoro.creationTime)
            set(it.pomodoroTime, pomodoro.pomodoroTime)
            set(it.shortBreakTime, pomodoro.shortBreakTime)
            set(it.longBreakTime, pomodoro.longBreakTime)
            set(it.progress, pomodoro.progress)
        }
    }
}
