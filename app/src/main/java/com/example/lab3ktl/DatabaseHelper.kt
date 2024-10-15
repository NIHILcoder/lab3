package com.example.lab3ktl

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "Students.db"
const val DATABASE_VERSION = 2
const val TABLE_NAME = "Groupmates"
const val COLUMN_ID = "ID"
const val COLUMN_LAST_NAME = "LastName"
const val COLUMN_FIRST_NAME = "FirstName"
const val COLUMN_MIDDLE_NAME = "MiddleName"
const val COLUMN_TIMESTAMP = "Timestamp"

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_LAST_NAME TEXT,
                $COLUMN_FIRST_NAME TEXT,
                $COLUMN_MIDDLE_NAME TEXT,
                $COLUMN_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()
        db.execSQL(createTable) // Выполняем команду создания
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Обновляем базу данных
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME") // Удаляем старую таблицу
        onCreate(db) // Создаем новую
    }

    // Метод для добавления сокурсника
    fun insertGroupmate(lastName: String, firstName: String, middleName: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LAST_NAME, lastName)
            put(COLUMN_FIRST_NAME, firstName)
            put(COLUMN_MIDDLE_NAME, middleName)
        }
        db.insert(TABLE_NAME, null, values) // Вставляем данные
        db.close() // Закрываем базу
    }

    // Метод для получения всех сокурсников
    fun getAllGroupmates(): Cursor {
        val db = readableDatabase // Открываем базу для чтения
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null) // Возвращаем данные
    }

    // Метод для очистки таблицы
    fun clearTable() {
        val db = writableDatabase // Открываем базу для записи
        db.execSQL("DELETE FROM $TABLE_NAME") // Удаляем все записи
        db.close() // Закрываем базу
    }

    // Метод для обновления имени последнего сокурсника
    fun updateLastGroupmateName(newName: String) {
        val db = writableDatabase // Открываем базу для записи
        db.execSQL("UPDATE $TABLE_NAME SET $COLUMN_LAST_NAME = ?, $COLUMN_FIRST_NAME = ?, $COLUMN_MIDDLE_NAME = ? WHERE $COLUMN_ID = (SELECT MAX($COLUMN_ID) FROM $TABLE_NAME)", arrayOf(newName.split(" ")[0], newName.split(" ")[1], newName.split(" ")[2]))
        db.close() // Закрываем базу
    }
}
