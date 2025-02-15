package com.oussama_chatri.data.daos

import com.oussama_chatri.DatabaseFactory.db
import com.oussama_chatri.data.entities.UserEntity
import com.oussama_chatri.data.model.User
import org.ktorm.dsl.*

object UserDao {
    fun createUser(name : String, username: String, email: String, password: String, level : Int = 1): Int {
        return db.insert(UserEntity) {
            set(it.name, name)
            set(it.username, username)
            set(it.email, email)
            set(it.password, password)
            set(it.level, level)
        }
    }

    fun findUserByEmail(email: String): User? {
        return db.from(UserEntity).select()
            .where { UserEntity.email eq email }
            .map {
                User(
                    id = it[UserEntity.id]!!,
                    name = it[UserEntity.name]!!,
                    username = it[UserEntity.username]!!,
                    email = it[UserEntity.email]!!,
                    password = it[UserEntity.password]!!,
                    level = it[UserEntity.level]!!
                )
            }.firstOrNull()
    }

    fun findUserByUsername(username: String): User? {
        return db.from(UserEntity).select()
            .where { UserEntity.username eq username }
            .map {
                User(
                    id = it[UserEntity.id]!!,
                    name = it[UserEntity.name]!!,
                    username = it[UserEntity.username]!!,
                    email = it[UserEntity.email]!!,
                    password = it[UserEntity.password]!!,
                    level = it[UserEntity.level]!!
                )
            }.firstOrNull()
    }

}