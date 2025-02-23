package com.oussama_chatri.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initModules(config : KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(mainModule)
    }
}