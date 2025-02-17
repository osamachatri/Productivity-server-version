package com.oussama_chatri

import org.ktorm.database.Database


object DatabaseFactory {
    val db: Database by lazy {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/productivity",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
        )
    }
}