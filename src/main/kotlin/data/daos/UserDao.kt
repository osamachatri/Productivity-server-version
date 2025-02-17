package com.oussama_chatri.data.daos

import com.oussama_chatri.DatabaseFactory
import com.oussama_chatri.data.entities.Accounts
import com.oussama_chatri.data.model.Account
import org.ktorm.database.Database
import org.ktorm.dsl.*

class AccountDao(private val database: Database = DatabaseFactory.db) {
    fun getAllAccounts(): List<Account> {
        return database.from(Accounts).select()
            .map { row -> Account(
                row[Accounts.userId]!!,
                row[Accounts.username]!!,
                row[Accounts.fullName]!!,
                row[Accounts.email]!!,
                row[Accounts.phoneNumber],
                row[Accounts.passwordHash]!!
            ) }
    }

    fun getAccountById(id: Int): Account? {
        return database.from(Accounts).select().where { Accounts.userId eq id }
            .map { row -> Account(
                row[Accounts.userId]!!,
                row[Accounts.username]!!,
                row[Accounts.fullName]!!,
                row[Accounts.email]!!,
                row[Accounts.phoneNumber],
                row[Accounts.passwordHash]!!
            ) }
            .firstOrNull()
    }

    fun getAccountByUsername(username: String): Account? {
        return database.from(Accounts).select().where{
            Accounts.username eq username
        }.map {row -> Account(
            row[Accounts.userId]!!,
            row[Accounts.username]!!,
            row[Accounts.fullName]!!,
            row[Accounts.email]!!,
            row[Accounts.phoneNumber],
            row[Accounts.passwordHash]!!
        )
        }.firstOrNull()
    }

    fun getAccountByEmail(email: String): Account? {
        return database.from(Accounts).select().where{
            Accounts.email eq email
        }.map {row -> Account(
            row[Accounts.userId]!!,
            row[Accounts.username]!!,
            row[Accounts.fullName]!!,
            row[Accounts.email]!!,
            row[Accounts.phoneNumber],
            row[Accounts.passwordHash]!!
        )
        }.firstOrNull()
    }

    fun getAccountByFullName(fullName: String): Account? {
        return database.from(Accounts).select().where{
            Accounts.fullName eq fullName
        }.map {row -> Account(
            row[Accounts.userId]!!,
            row[Accounts.username]!!,
            row[Accounts.fullName]!!,
            row[Accounts.email]!!,
            row[Accounts.phoneNumber],
            row[Accounts.passwordHash]!!
        )
        }.firstOrNull()
    }

    fun getAccountByPhoneNumber(phoneNumber: String): Account? {
        return database.from(Accounts).select().where{
            Accounts.phoneNumber eq phoneNumber
        }.map {row -> Account(
            row[Accounts.userId]!!,
            row[Accounts.username]!!,
            row[Accounts.fullName]!!,
            row[Accounts.email]!!,
            row[Accounts.phoneNumber],
            row[Accounts.passwordHash]!!
        )
        }.firstOrNull()
    }

    fun createAccount(
        username: String, fullName: String, email: String, phoneNumber: String?, passwordHash: String
    ) : Int {
        return database.insert(Accounts) {
            set(it.username, username)
            set(it.fullName, fullName)
            set(it.email, email)
            set(it.phoneNumber, phoneNumber)
            set(it.passwordHash, passwordHash)
        }
    }
}

