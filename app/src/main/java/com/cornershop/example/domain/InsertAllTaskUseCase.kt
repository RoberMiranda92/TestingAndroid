package com.cornershop.example.domain

import com.cornershop.example.data.TaskRepository

class InsertAllTaskUseCase(private val repository: TaskRepository) {

    fun execute() = repository.insertTask()
}