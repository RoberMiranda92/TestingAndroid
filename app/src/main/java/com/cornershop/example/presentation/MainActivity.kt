package com.cornershop.example.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cornershop.example.R
import com.cornershop.example.ViewModelFactory
import com.cornershop.example.data.Task

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelFactory()).get(MainViewModel::class.java)


        viewModel.taskLiveData.observe(this, Observer {
            showTask(it)
        })
        findViewById<AppCompatButton>(R.id.btn_get).setOnClickListener { viewModel.getAllTask() }
        findViewById<AppCompatButton>(R.id.btn_insert).setOnClickListener { insertTask() }
    }

    private fun insertTask() {
        viewModel.insertAllTask()
        findViewById<TextView>(R.id.task_text).text = "Data inserted"
    }

    private fun showTask(list: List<Task>) {
        findViewById<TextView>(R.id.task_text).text = list.toString()
    }
}