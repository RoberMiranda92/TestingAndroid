package com.cornershop.example.domain

import com.cornershop.example.data.Task
import com.cornershop.example.data.TaskRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllTaskUseCase(private val repository: TaskRepository) {

    fun execute(): List<Task> = repository.getAllTask()

    suspend fun executeSuspendable(): List<Task> =
        withContext(Dispatchers.IO) {
            repository.getAllTaskSuspendable()
        }


    fun executeUseCaseObserver(): Observable<List<Task>> {
        return repository.getAllTaskFromObservable()
    }
}