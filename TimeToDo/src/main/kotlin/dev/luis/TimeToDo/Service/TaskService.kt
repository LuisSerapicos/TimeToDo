package dev.luis.TimeToDo.Service

import dev.luis.TimeToDo.Model.Task
import dev.luis.TimeToDo.Model.TaskStatus
import dev.luis.TimeToDo.Repository.TaskRepository
import dev.luis.TimeToDo.TaskException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskService {
    @Autowired
    private lateinit var taskRepository: TaskRepository

    fun getAllTasks(): List<Task> {
        return try {
            taskRepository.findAll()
        } catch (e: Exception) {
            println(e.message)
            throw TaskException("Failed to get all tasks: ${e.message}")
        }
    }

    fun getTaskByName(name: String): Optional<Task> {
        return try {
            taskRepository.getTaskByName(name)
        } catch (e: Exception) {
            println(e.message)
            throw TaskException("Failed to get task: ${e.message}")
        }
    }

    fun getTaskById(id: ObjectId): Optional<Task> {
        return try {
            taskRepository.getTaskById(id)
        } catch (e: Exception) {
            println(e.message)
            throw TaskException("Failed to get task: ${e.message}")
        }
    }

    fun createTask(name: String, status: TaskStatus, description: String): Task {
        return try {
            val newTask = taskRepository.insert(Task(name = name, status = status, description = description))
            newTask
        } catch (e: Exception) {
            println(e.message)
            throw TaskException("Failed to create task: ${e.message}")
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
            println(e.message)
            throw TaskException("Failed to delete task: ${e.message}")

        }
    }

    fun editTask(id: ObjectId, task: Task) {
        try {
            val existingTask: Task? = taskRepository.getTaskById(id).orElse(null)
            if (existingTask != null) {
                // Update task properties if necessary
                existingTask.name = task.name ?: existingTask.name
                existingTask.status = task.status ?: existingTask.status
                existingTask.description = task.description ?: existingTask.description
                taskRepository.save(existingTask)
            }
        } catch (e: Exception) {
            println(e.message)
            throw TaskException("Failed to edit task: ${e.message}")
        }
    }
}