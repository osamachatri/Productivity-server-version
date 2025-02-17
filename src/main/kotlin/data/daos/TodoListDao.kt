package com.oussama_chatri.data.daos

import com.oussama_chatri.data.entities.ToDoLists
import com.oussama_chatri.data.model.ToDoList
import com.oussama_chatri.data.model.Work
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.ktorm.database.Database
import org.ktorm.dsl.*

class ToDoListDao(private val database: Database) {

    private val json = Json { prettyPrint = true }

    // Get all ToDoLists from the database
    fun getAllToDoLists(): List<ToDoList> {
        return database.from(ToDoLists).select()
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
                    priority = row[ToDoLists.priority]!!,
                    isPinned = row[ToDoLists.isPinned]!!,
                    progress = row[ToDoLists.progress]!!
                )
            }
    }

    // Create a new ToDoList
    fun createToDoList(toDoList: ToDoList) {
        val serializedWorks = json.encodeToString(toDoList.listOfWorks)

        database.insert(ToDoLists) {
            set(it.ownerId, toDoList.ownerId)
            set(it.title, toDoList.title)
            set(it.description, toDoList.description)
            set(it.listOfWorks, serializedWorks)
            set(it.creationTime, toDoList.creationTime)
            set(it.priority, toDoList.priority)
            set(it.isPinned, toDoList.isPinned)
            set(it.progress, toDoList.progress)
        }
    }

    // Update an existing ToDoList
    fun updateToDoList(toDoList: ToDoList) {
        val serializedWorks = json.encodeToString(toDoList.listOfWorks)  // Serialize works list to JSON string

        database.update(ToDoLists) {
            set(it.ownerId, toDoList.ownerId)
            set(it.title, toDoList.title)
            set(it.description, toDoList.description)
            set(it.listOfWorks, serializedWorks)
            set(it.creationTime, toDoList.creationTime)
            set(it.priority, toDoList.priority)
            set(it.isPinned, toDoList.isPinned)
            set(it.progress, toDoList.progress)
            where { it.id eq toDoList.id }
        }
    }
}

