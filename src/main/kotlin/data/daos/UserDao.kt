package com.oussama_chatri.data.daos

import com.oussama_chatri.data.entities.Accounts
import com.oussama_chatri.data.model.Account
import org.ktorm.database.Database
import org.ktorm.dsl.*

class AccountDao(private val database: Database) {
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

    fun createAccount(account: Account) {
        database.insert(Accounts) {
            set(it.username, account.username)
            set(it.fullName, account.fullName)
            set(it.email, account.email)
            set(it.phoneNumber, account.phoneNumber)
            set(it.passwordHash, account.passwordHash)
        }
    }
}

