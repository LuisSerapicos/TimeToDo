package dev.luis.TimeToDo.Service

import dev.luis.TimeToDo.Model.Task
import dev.luis.TimeToDo.Repository.TaskRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService {
    @Autowired
    private lateinit var taskRepository: TaskRepository

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    fun getAllTasks(): List<Task> {
        return try {
            taskRepository.findAll()
        } catch (e: Exception) {
            // Log the exception or handle it appropriately
            emptyList() // Return an empty list or handle the error in some other way
        }
    }

    fun getTaskByName(name: String): Optional<Task> {
        return try {
            taskRepository.getTaskByName(name)
        } catch (e: Exception) {
            // Log the exception or handle it appropriately
            Optional.empty() // Return an empty Optional or handle the error in some other way
        }
    }

    fun getTaskById(id: ObjectId): Optional<Task> {
        return try {
            taskRepository.getTaskById(id)
        } catch (e: Exception) {
            // Log the exception or handle it appropriately
            Optional.empty() // Return an empty Optional or handle the error in some other way
        }
    }

    fun createTask(name: String): Task {
        return try {
            val newTask = taskRepository.insert(Task(name = name, status = "To Do"))
            newTask
        } catch (e: Exception) {
            // Log the exception or handle it appropriately
            // You may want to throw a custom exception here or handle the error in some other way
            Task() // Return an empty Task object or handle the error in some other way
        }
    }

    fun deleteTask(id: ObjectId) {
        try {
            val optionalTask: Optional<Task> = taskRepository.getTaskById(id)
            if (optionalTask.isPresent) {
                val task: Task = optionalTask.get()
                taskRepository.delete(task)
            }
        } catch (e: Exception) {
            // Log the exception or handle it appropriately
        }
    }

    fun editTask(id: ObjectId, task: Task) {
        try {
            val existingTask: Task? = taskRepository.getTaskById(id).orElse(null)
            if (existingTask != null) {
                // Update task properties if necessary
                existingTask.name = task.name ?: existingTask.name
                existingTask.status = task.status ?: existingTask.status
                taskRepository.save(existingTask)
            }
        } catch (e: Exception) {
            // Log the exception or handle it appropriately
        }
    }
}