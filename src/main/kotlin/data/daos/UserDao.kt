package com.oussama_chatri.data.daos

import com.oussama_chatri.DatabaseFactory.db
import com.oussama_chatri.data.entities.Users
import com.oussama_chatri.data.model.User
import org.ktorm.dsl.*

class UserDao {
    fun getAllUsers(): List<User> {
        return db.from(Users).select().map { row ->
            User(
                userId = row[Users.userId],
                username = row[Users.username]!!,
                fullName = row[Users.fullName]!!,
                email = row[Users.email]!!,
                phoneNumber = row[Users.phoneNumber],
                passwordHash = row[Users.passwordHash]!!
            )
        }
    }

    fun getUserById(id: Int): User? {
        return db.from(Users).select().where { Users.userId eq id }.map { row ->
            User(
                userId = row[Users.userId],
                username = row[Users.username]!!,
                fullName = row[Users.fullName]!!,
                email = row[Users.email]!!,
                phoneNumber = row[Users.phoneNumber],
                passwordHash = row[Users.passwordHash]!!
            )
        }.firstOrNull()
    }

    fun addUser(user: User): Boolean {
        val inserted = db.insert(Users) {
            set(Users.username, user.username)
            set(Users.fullName, user.fullName)
            set(Users.email, user.email)
            set(Users.phoneNumber, user.phoneNumber)
            set(Users.passwordHash, user.passwordHash)
        }
        return inserted > 0
    }
}
