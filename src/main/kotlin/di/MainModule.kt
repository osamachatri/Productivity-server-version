package com.oussama_chatri.di

import com.oussama_chatri.DatabaseFactory
import com.oussama_chatri.data.daos.EventDao
import com.oussama_chatri.data.daos.NoteDao
import com.oussama_chatri.data.daos.ToDoListDao
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {
    single {
        DatabaseFactory.db
    }.bind()

    single {
        NoteDao(get())
    }

    single {
        ToDoListDao(get())
    }

    single {
        EventDao(get())
    }
}