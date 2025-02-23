package com.oussama_chatri.di

import com.oussama_chatri.DatabaseFactory
import com.oussama_chatri.data.daos.*
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

    single {
        PomodoroDao(get())
    }

    single {
        NotificationDao(get())
    }
}