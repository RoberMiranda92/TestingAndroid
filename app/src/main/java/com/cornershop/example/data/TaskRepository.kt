package com.cornershop.example.data

import android.content.Context
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer

class TaskRepository(private val context: Context) {

    fun getAllTask(): List<Task> {
        val sh = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val task: List<String> = sh?.let {
            it.getString(TASK_KEY, "")?.split(DELIMITER)
        } ?: emptyList()

        return task.map { Task(it) }
    }

    suspend fun getAllTaskSuspendable():List<Task> {
        return getAllTask()
    }

    fun insertTask() {
        val sh = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sh?.edit()
        editor?.putString(TASK_KEY, "TASK1${DELIMITER}TASK2")
        editor?.apply()
    }

    fun getAllTaskFromObservable(): Observable<List<Task>>{
        return  Observable.create<List<Task>> { getAllTask() }
    }

    companion object {
        const val PREF_NAME = "TASK_REPOSITORY"
        const val TASK_KEY = "task_key"
        const val DELIMITER = "#"
    }
}