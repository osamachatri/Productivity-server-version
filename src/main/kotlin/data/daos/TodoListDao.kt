package com.oussama_chatri.data.daos
import com.oussama_chatri.DatabaseFactory.db
import com.oussama_chatri.data.entities.TodoLists
import com.oussama_chatri.data.model.TodoList
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class TodoListDao {
    fun getAllTodos(): List<TodoList> {
        return db.from(TodoLists).select().map { row ->
            TodoList(
                id = row[TodoLists.id],
                ownerId = row[TodoLists.ownerId]!!,
                title = row[TodoLists.title]!!,
                description = row[TodoLists.description]!!,
                creationTime = row[TodoLists.creationTime].toString(),
                isCompleted = row[TodoLists.isCompleted] ?: false
            )
        }
    }

    fun addTodo(todo: TodoList): Boolean {
        val inserted = db.insert(TodoLists) {
            set(TodoLists.ownerId, todo.ownerId)
            set(TodoLists.title, todo.title)
            set(TodoLists.description, todo.description)
            set(TodoLists.isCompleted, todo.isCompleted)
        }
        return inserted > 0
    }
}
