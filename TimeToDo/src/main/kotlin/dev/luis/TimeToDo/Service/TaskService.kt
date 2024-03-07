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
        return taskRepository.findAll()
    }

    fun getTaskByName(name: String): Optional<Task> {
        return taskRepository.getTaskByName(name)
    }

    fun getTaskById(id: ObjectId): Optional<Task> {
        return taskRepository.getTaskById(id)
    }

    fun createTask(name: String): Task {
        val newTask = taskRepository.insert(Task(name = name, status = "To Do"))

        return newTask
    }

    fun deleteTask(id: ObjectId) {
        val optionalTask: Optional<Task> = taskRepository.getTaskById(id)

        if (optionalTask.isPresent) {
            val task: Task = optionalTask.get()
            taskRepository.delete(task)
        }
    }

    fun editTask(id: ObjectId, task: Task) {
        val task: Task? = taskRepository.getTaskById(id).orElse(null)

        if (task != null) {
            if (task.name != null) {
                task.name = task.name
            }
            if (task.status != null) {
                task.status = task.status
            }
            taskRepository.save(task)
        }
    }
}