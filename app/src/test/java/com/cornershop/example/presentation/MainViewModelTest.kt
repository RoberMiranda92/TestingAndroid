package com.cornershop.example.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cornershop.example.data.Task
import com.cornershop.example.domain.GetAllTaskUseCase
import com.cornershop.example.domain.InsertAllTaskUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: GetAllTaskUseCase

    @MockK
    private lateinit var insertUseCase: InsertAllTaskUseCase

    @RelaxedMockK
    private lateinit var observer: Observer<List<Task>>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(insertUseCase, useCase)
    }

    @Test
    fun getListFromUseCase() {
        //Given
        val tasks: List<Task> = arrayOf(
            Task("Test 1"),
            Task("Test 2")
        ).toList()

        //when
        every { useCase.execute() } returns tasks

        viewModel.taskLiveData.observeForever(observer)
        viewModel.getAllTask()

        //Then
        verify { useCase.execute() }
        verify { observer.onChanged(tasks) }
        confirmVerified(observer)
        confirmVerified(useCase)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getListFromUseCaseSuspendable() = runBlockingTest {
        //Given
        val tasks: List<Task> = arrayOf(
            Task("Test 1"),
            Task("Test 2")
        ).toList()

        //when
        coEvery { useCase.executeSuspendable() } returns tasks

        viewModel.taskLiveData.observeForever(observer)
        viewModel.getAllTaskWithCoroutine()

        //Then
        coVerify { useCase.executeSuspendable() }
        coVerify { observer.onChanged(tasks) }
        confirmVerified(observer)
        confirmVerified(useCase)
    }

    @Test
    fun getListFromObservable() {
        //Given
        val tasks: List<Task> = arrayOf(
            Task("Test 1"),
            Task("Test 2")
        ).toList()

        //when
        every { useCase.executeUseCaseObserver() } returns Observable.just(tasks)

        viewModel.taskLiveData.observeForever(observer)
        viewModel.getAllTaskWithRx()

        //Then
        verify { useCase.executeUseCaseObserver() }
        verify { observer.onChanged(tasks) }
        confirmVerified(observer)
        confirmVerified(useCase)
    }
}