package com.cornershop.example.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornershop.example.data.Task
import com.cornershop.example.domain.GetAllTaskUseCase
import com.cornershop.example.domain.InsertAllTaskUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val insertUseCase: InsertAllTaskUseCase,
    private val getUseCase: GetAllTaskUseCase
) : ViewModel() {

    val taskLiveData: MutableLiveData<List<Task>> = MutableLiveData()

    fun insertAllTask() {
        insertUseCase.execute()
    }

    fun getAllTask() {
        val list = getUseCase.execute()
        taskLiveData.value = list
    }

    fun getAllTaskWithCoroutine() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUseCase.executeSuspendable()
            taskLiveData.postValue(result)
        }
    }

    fun getAllTaskWithRx() {
        getUseCase.executeUseCaseObserver()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                taskLiveData.value = it
            }
    }
}