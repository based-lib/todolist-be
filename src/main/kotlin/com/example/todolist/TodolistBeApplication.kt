package com.example.todolist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodolistBeApplication

fun main(args: Array<String>) {
    runApplication<TodolistBeApplication>(*args)
}
