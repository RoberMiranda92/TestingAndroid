package com.cornershop.example.data

import android.content.Context
import android.content.SharedPreferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskRepositoryTest {
    private lateinit var repository: TaskRepository
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
        repository = TaskRepository(context)
    }

    @After
    fun clearDown() {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(TaskRepository.PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    @Test
    fun testGet() {
        //GIVEN
        val task: List<Task> = arrayListOf(Task("TASK1"), Task("TASK2")).toList()
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(TaskRepository.PREF_NAME, Context.MODE_PRIVATE)
        //WHEN
        val editor = sharedPreferences.edit()
        editor.putString(TaskRepository.TASK_KEY, "${task[0].name}${TaskRepository.DELIMITER}${task[1].name}")
        editor.apply()

        //THEN
        assertEquals(task, repository.getAllTask())
    }
}