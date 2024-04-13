package me.lab.happyprimo.data.local.dao

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import android.content.Context


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "my_database", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE my_table (id INTEGER PRIMARY KEY AUTOINCREMENT, feed_data TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema changes here if needed
    }
}

