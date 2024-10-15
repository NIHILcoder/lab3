package com.example.lab3ktl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализируем помощник базы данных
        databaseHelper = DatabaseHelper(this)

        // Очищаем таблицу перед добавлением данных
        databaseHelper.clearTable()
        addInitialGroupmates() // Добавляем начальных сокурсников

        // Находим кнопки по их идентификаторам
        val viewGroupmatesButton: Button = findViewById(R.id.viewGroupmatesButton)
        val addGroupmateButton: Button = findViewById(R.id.addGroupmateButton)
        val updateLastGroupmateButton: Button = findViewById(R.id.updateLastGroupmateButton)

        // Устанавливаем обработчик нажатия для кнопки просмотра сокурсников
        viewGroupmatesButton.setOnClickListener {
            val intent = Intent(this, GroupmatesActivity::class.java)
            startActivity(intent) // Переходим на экран сокурсников
        }

        // Устанавливаем обработчик нажатия для кнопки добавления сокурсника
        addGroupmateButton.setOnClickListener {
            // Добавляем нового сокурсника
            databaseHelper.insertGroupmate("Костопраев", "Дмитрий", "Евгеньевич")
        }

        // Устанавливаем обработчик нажатия для кнопки обновления последнего сокурсника
        updateLastGroupmateButton.setOnClickListener {
            // Обновляем имя последнего добавленного сокурсника
            databaseHelper.updateLastGroupmateName("Костопраев Дмитрий Евгеньевич")
        }
    }

    private fun addInitialGroupmates() {
        // Добавляем тестовых сокурсников в базу данных
        databaseHelper.insertGroupmate("TEST1", "TEST2", "TEST3")
        databaseHelper.insertGroupmate("TEST11", "TEST21", "TEST31")
        databaseHelper.insertGroupmate("TEST22", "TEST22", "TEST32")
        databaseHelper.insertGroupmate("TEST33", "TEST23", "TEST33")
        databaseHelper.insertGroupmate("TEST44", "TEST24", "TEST34")
    }
}
