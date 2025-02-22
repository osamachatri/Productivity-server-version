package com.oussama_chatri.di

import com.oussama_chatri.DatabaseFactory
import com.oussama_chatri.data.daos.NoteDao
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {
    single {
        DatabaseFactory.db
    }.bind()

    single {
        NoteDao(get())
    }
}