package com.oussama_chatri.data.daos

import com.oussama_chatri.DatabaseFactory
import com.oussama_chatri.data.entities.ToDoLists
import com.oussama_chatri.data.model.Work
import com.oussama_chatri.Api.requests.ToDoListRequest
import com.oussama_chatri.Api.requests.UpdatedToDoListRequest
import com.oussama_chatri.Api.responses.ToDoListResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.ktorm.database.Database
import org.ktorm.dsl.*

class ToDoListDao(private val database: Database = DatabaseFactory.db) {

    private val json = Json { prettyPrint = true }

    // Get all ToDoLists from the database
    fun getAllToDoLists(
        ownerId: String
    ): List<ToDoListResponse> {
        return database.from(ToDoLists).select()
            .where { ToDoLists.ownerId eq ownerId }
            .map { row ->
                val worksJson = row[ToDoLists.listOfWorks]!!
                val worksList: List<Work> = json.decodeFromString(worksJson)
                ToDoListResponse(
                    title = row[ToDoLists.title]!!,
                    description = row[ToDoLists.description],
                    listOfWorks = worksList,
                    creationTime = row[ToDoLists.creationTime]!!,
                    editedTime = row[ToDoLists.editedTime]!!,
                    priority = row[ToDoLists.priority]!!,
                    isPinned = row[ToDoLists.isPinned]!!,
                    progress = row[ToDoLists.progress]!!
                )
            }
    }

    // Create a new ToDoList
    fun createToDoList(
        ownerId: String,
        request: ToDoListRequest
    ): Int {
        val serializedWorks = json.encodeToString(request.listOfWorks)

        return database.insert(ToDoLists) {
            set(it.ownerId, ownerId)
            set(it.title, request.title)
            set(it.description, request.description)
            set(it.listOfWorks, serializedWorks)
            set(it.creationTime, request.creationTime)
            set(it.editedTime, request.creationTime)
            set(it.priority, request.priority)
            set(it.isPinned, request.isPinned)
            set(it.progress, request.progress)
        }
    }

    // Update an existing ToDoList
    fun updateToDoList(
        id: Int,
        ownerId: String,
        request: UpdatedToDoListRequest
    ): Boolean {
        val serializedWorks = json.encodeToString(request.listOfWorks)  // Serialize works list to JSON string

        val affectedRows = database.update(ToDoLists) {
            set(it.title, request.title)
            set(it.description, request.description)
            set(it.listOfWorks, serializedWorks)
            set(it.editedTime, request.editedTime)
            set(it.priority, request.priority)
            set(it.isPinned, request.isPinned)
            set(it.progress, request.progress)
            where { (it.id eq id) and (it.ownerId eq ownerId) }
        }

        return affectedRows > 0
    }

    fun deleteToDoList(toDoListId: Int, ownerId: String): Boolean {
        val affectedRows = database.delete(ToDoLists) {
            (ToDoLists.id eq toDoListId) and (ToDoLists.ownerId eq ownerId)
        }
        return affectedRows > 0
    }
}

