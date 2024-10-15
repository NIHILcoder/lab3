package com.example.lab3ktl

import android.database.Cursor
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GroupmatesActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groupmates)

        // Инициализируем помощник базы данных
        databaseHelper = DatabaseHelper(this)
        val tvGroupmates: TextView = findViewById(R.id.tvGroupmates) // Находим TextView для отображения сокурсников

        // Получаем курсор с данными всех сокурсников
        val cursor: Cursor = databaseHelper.getAllGroupmates()
        val groupmatesList = StringBuilder() // Создаем StringBuilder для формирования списка сокурсников

        // Проверяем, есть ли данные в курсоре
        if (cursor.moveToFirst()) {
            do {
                // Получаем индексы нужных столбцов
                val idIndex = cursor.getColumnIndex(COLUMN_ID)
                val lastNameIndex = cursor.getColumnIndex(COLUMN_LAST_NAME)
                val firstNameIndex = cursor.getColumnIndex(COLUMN_FIRST_NAME)
                val middleNameIndex = cursor.getColumnIndex(COLUMN_MIDDLE_NAME)
                val timestampIndex = cursor.getColumnIndex(COLUMN_TIMESTAMP)

                // Проверяем, что все индексы валидны
                if (idIndex != -1 && lastNameIndex != -1 && firstNameIndex != -1 && middleNameIndex != -1 && timestampIndex != -1) {
                    // Получаем значения из курсора по индексам
                    val id = cursor.getInt(idIndex)
                    val lastName = cursor.getString(lastNameIndex)
                    val firstName = cursor.getString(firstNameIndex)
                    val middleName = cursor.getString(middleNameIndex)
                    val timestamp = cursor.getString(timestampIndex)

                    // Формируем строку с информацией о сокурснике и добавляем ее в список
                    groupmatesList.append("ID: $id, ФИО: $lastName $firstName $middleName, Время: $timestamp\n")
                } else {
                    // Если один из индексов не найден, добавляем сообщение об ошибке
                    groupmatesList.append("Ошибка: один из индексов столбцов не найден.\n")
                }
            } while (cursor.moveToNext()) // Переходим к следующей записи в курсоре
        }

        // Отображаем собранный список сокурсников в TextView
        tvGroupmates.text = groupmatesList.toString()
        cursor.close() // Закрываем курсор после использования
    }
}
