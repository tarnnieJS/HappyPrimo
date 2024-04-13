package me.lab.happyprimo.data.repositories

import android.content.ContentValues
import me.lab.happyprimo.data.local.dao.DatabaseHelper
import me.lab.happyprimo.data.models.FeedData

class DatabaseRepository(private val dbHelper: DatabaseHelper) {


    fun saveFeedData(feedData: FeedData): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("feed_data", feedData.feedData)
        }

        db.beginTransaction()
        try {
            val id = feedData.id
            if (id != null && id != -1) {
                // Check if data with the given ID exists
                val cursor = db.query(
                    "my_table",
                    null,
                    "id = ?",
                    arrayOf(id.toString()),
                    null,
                    null,
                    null
                )
                val dataExists = cursor.count > 0
                cursor.close()

                if (dataExists) {
                    // Update existing data
                    val rowsAffected = db.update("my_table", values, "id = ?", arrayOf(id.toString()))
                    db.setTransactionSuccessful()
                    return rowsAffected > 0
                } else {
                    // Insert new data
                    val newRowId = db.insert("my_table", null, values)
                    db.setTransactionSuccessful()
                    return newRowId != -1L
                }
            } else {
                // If ID is null or -1, insert new data
                val newRowId = db.insert("my_table", null, values)
                db.setTransactionSuccessful()
                return newRowId != -1L
            }
        } catch (e: Exception) {
            // Handle any exceptions
            return false
        } finally {
            db.endTransaction()
            db.close()
        }
    }



    fun getFeedData(): FeedData? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM my_table", null)
        var feedData: FeedData? = null
        cursor.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex("id")
                val feedIndex = it.getColumnIndex("feed_data")
                if (idIndex != -1 && feedIndex != -1) {
                    val id = it.getInt(idIndex)
                    val feed = it.getString(feedIndex)
                    feedData = FeedData(id, feed)
                }
            }
        }
        return feedData
    }

}