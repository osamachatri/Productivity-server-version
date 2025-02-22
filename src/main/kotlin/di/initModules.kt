package com.oussama_chatri.di

import org.koin.core.context.startKoin

fun initModules(){
    startKoin {
        modules(mainModule)
    }
}