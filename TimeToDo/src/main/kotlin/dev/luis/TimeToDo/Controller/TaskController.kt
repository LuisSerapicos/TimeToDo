package dev.luis.TimeToDo.Controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.luis.TimeToDo.Model.Task
import dev.luis.TimeToDo.Model.TaskStatus
import dev.luis.TimeToDo.Service.TaskService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.util.*

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = ["http://localhost:3000"])
class TaskController {
    @Autowired
    private lateinit var taskService: TaskService

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<Task>> {
        return try {
            val tasks = taskService.getAllTasks()

            if (tasks.isNotEmpty())
                ResponseEntity.ok(tasks)
            else
                ResponseEntity.notFound().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/name/{name}")
    fun getTaskByName(@PathVariable name: String?): ResponseEntity<Optional<Task>> {
        return try {
            val task = name?.let { taskService.getTaskByName(it) }

            if (task?.isPresent == true)
                ResponseEntity.ok(task)
            else
                ResponseEntity.notFound().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: String?): ResponseEntity<Optional<Task>> {
        return try {
            val taskId = id?.let { ObjectId(it) }
            val task = taskId?.let { taskService.getTaskById(it) }

            if (task?.isPresent == true)
                ResponseEntity.ok(task)
            else
                ResponseEntity.notFound().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PostMapping
    fun createTask(@RequestBody payload: Map<String, String>): ResponseEntity<Task> {
        return try {
            val name = payload["name"]
            val status = payload["status"]?.let { TaskStatus.valueOf(it.toUpperCase()) }
            val description = payload["description"]

            if ((name?.length ?: 0) >= 3 && status != null) {
                val newTask = name?.let { taskService.createTask(it, status, description ?: "") }
                ResponseEntity.status(HttpStatus.CREATED).body(newTask)
            } else {
                ResponseEntity.badRequest().build()
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: String?): ResponseEntity<Unit> {
        return try {
            if (id != null && ObjectId.isValid(id)) {
                val objectId = ObjectId(id)
                if (taskService.getTaskById(objectId).isPresent) {
                    taskService.deleteTask(objectId)
                    return ResponseEntity.ok().build()
                }
                    ResponseEntity.badRequest().build()
            }
            else
                ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PutMapping("/{id}")
    fun editTask(@PathVariable id: String?, @RequestBody task: Task): ResponseEntity<Unit> {
        return try {
            if (id != null && ObjectId.isValid(id)) {
                val objectId = ObjectId(id)
                if (taskService.getTaskById(objectId).isPresent) {
                    taskService.editTask(objectId, task)
                    return ResponseEntity.ok().build()
                }
                else
                    return ResponseEntity.badRequest().build()
            }
            else
                ResponseEntity.badRequest().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}