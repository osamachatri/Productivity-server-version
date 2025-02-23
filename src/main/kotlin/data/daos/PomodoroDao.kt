package com.oussama_chatri.data.daos

import com.oussama_chatri.Api.requests.PomodoroRequest
import com.oussama_chatri.Api.requests.UpdatedPomodoroRequest
import com.oussama_chatri.Api.responses.PomodoroResponse
import com.oussama_chatri.data.entities.Pomodoros
import org.ktorm.database.Database
import org.ktorm.dsl.*

class PomodoroDao(private val database: Database) {

    fun getAllPomodoros(ownerId : String): List<PomodoroResponse> {
        return database.from(Pomodoros).select()
            .where { Pomodoros.ownerId eq ownerId }
            .map { row -> PomodoroResponse(
                row[Pomodoros.priority]!!,
                row[Pomodoros.title]!!,
                row[Pomodoros.description],
                row[Pomodoros.numPomodoros]!!,
                row[Pomodoros.creationTime]!!,
                row[Pomodoros.editedTime]!!,
                row[Pomodoros.pomodoroTime]!!,
                row[Pomodoros.shortBreakTime]!!,
                row[Pomodoros.longBreakTime]!!,
                row[Pomodoros.progress]!!,
                ) }
    }

    fun createPomodoro(ownerId: String, request: PomodoroRequest) : Int {
        return database.insert(Pomodoros) {
            set(it.ownerId, ownerId)
            set(it.priority, request.priority)
            set(it.title, request.title)
            set(it.description, request.description)
            set(it.numPomodoros, request.numPomodoros)
            set(it.creationTime, request.creationTime)
            set(it.editedTime, request.creationTime)
            set(it.pomodoroTime, request.pomodoroTime)
            set(it.shortBreakTime, request.shortBreakTime)
            set(it.longBreakTime, request.longBreakTime)
            set(it.progress, request.progress)
        }
    }

    fun updatePomodoro(
        pomodoroId: Int, ownerId: String,
        request: UpdatedPomodoroRequest
    ): Boolean {
        val affectedRows = database.update(Pomodoros) {
            set(it.ownerId, ownerId)
            set(it.priority, request.priority)
            set(it.title, request.title)
            set(it.description, request.description)
            set(it.numPomodoros, request.numPomodoros)
            set(it.editedTime, request.editedTime)
            set(it.pomodoroTime, request.pomodoroTime)
            set(it.shortBreakTime, request.shortBreakTime)
            set(it.longBreakTime, request.longBreakTime)
            set(it.progress, request.progress)
            where { (Pomodoros.id eq pomodoroId) and (Pomodoros.ownerId eq ownerId) }
        }
        return affectedRows > 0
    }

    fun deletePomodoro(pomodoroId: Int, ownerId: String): Boolean {
        val affectedRows = database.delete(Pomodoros) {
            (Pomodoros.id eq pomodoroId) and (Pomodoros.ownerId eq ownerId)
        }
        return affectedRows > 0
    }
}
