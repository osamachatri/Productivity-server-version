package com.oussama_chatri.data.daos

import com.oussama_chatri.DatabaseFactory
import com.oussama_chatri.data.entities.ToDoLists
import com.oussama_chatri.data.model.ToDoList
import com.oussama_chatri.data.model.Work
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.ktorm.database.Database
import org.ktorm.dsl.*

class ToDoListDao(private val database: Database = DatabaseFactory.db) {

    private val json = Json { prettyPrint = true }

    // Get all ToDoLists from the database
    fun getAllToDoLists(
        ownerId: String
    ): List<ToDoList> {
        return database.from(ToDoLists).select()
            .where { ToDoLists.ownerId eq ownerId }
            .map { row ->
                val worksJson = row[ToDoLists.listOfWorks]!!
                val worksList: List<Work> = json.decodeFromString(worksJson)
                ToDoList(
                    id = row[ToDoLists.id]!!,
                    ownerId = row[ToDoLists.ownerId]!!,
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
        title: String,
        description: String?,
        listOfWorks: List<Work>,
        creationTime: Long,
        priority: String,
        isPinned: Boolean,
        progress: Float
    ): Int {
        val serializedWorks = json.encodeToString(listOfWorks)

        return database.insert(ToDoLists) {
            set(it.ownerId, ownerId)
            set(it.title, title)
            set(it.description, description)
            set(it.listOfWorks, serializedWorks)
            set(it.creationTime, creationTime)
            set(it.editedTime, creationTime)
            set(it.priority, priority)
            set(it.isPinned, isPinned)
            set(it.progress, progress)
        }
    }

    // Update an existing ToDoList
    fun updateToDoList(
        id: Int,
        ownerId: String,
        title: String,
        description: String?,
        listOfWorks: List<Work>,
        editedTime: Long,
        priority: String,
        isPinned: Boolean,
        progress: Float
    ) : Boolean{
        val serializedWorks = json.encodeToString(listOfWorks)  // Serialize works list to JSON string

        val affectedRows = database.update(ToDoLists) {
            set(it.title, title)
            set(it.description, description)
            set(it.listOfWorks, serializedWorks)
            set(it.editedTime, editedTime)
            set(it.priority, priority)
            set(it.isPinned, isPinned)
            set(it.progress, progress)
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

