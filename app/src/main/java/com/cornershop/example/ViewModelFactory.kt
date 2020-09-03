package com.cornershop.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornershop.example.data.TaskRepository
import com.cornershop.example.domain.GetAllTaskUseCase
import com.cornershop.example.domain.InsertAllTaskUseCase
import com.cornershop.example.presentation.MainViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> {
                    val repository: TaskRepository = TaskRepository(Injector.context!!)
                    val insertUseCase = InsertAllTaskUseCase(repository)
                    val getAllTaskUseCase = GetAllTaskUseCase(repository)

                    MainViewModel(insertUseCase, getAllTaskUseCase)
                }
                else -> error("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}