package dev.luis.TimeToDo.Controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.luis.TimeToDo.Model.Task
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
class TaskController {
    @Autowired
    private lateinit var taskService: TaskService

    @GetMapping("/all")
    fun getAll(): ResponseEntity<List<Task>> {
        return ResponseEntity<List<Task>>(taskService.getAllTasks(), HttpStatus.OK)
    }

    @GetMapping("/name/{name}")
    fun getMovieByName(@PathVariable name: String?): ResponseEntity<Optional<Task>> {
        return ResponseEntity<Optional<Task>>(name?.let { taskService.getTaskByName(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getMovieByName(@PathVariable id: ObjectId?): ResponseEntity<Optional<Task>> {
        return ResponseEntity<Optional<Task>>(id?.let { taskService.getTaskById(it) }, HttpStatus.OK)
    }

    @PostMapping
    fun createTask(@RequestBody payload: Map<String, String>): ResponseEntity<Task> {
        val newTask = payload.get("name")?.let { taskService.createTask(it) }

        return ResponseEntity<Task>(newTask, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: String?): ResponseEntity<Unit> {
        if (id != null && ObjectId.isValid(id)) {
            val objectId = ObjectId(id)
            if (taskService.getTaskById(objectId).isPresent) {
                taskService.deleteTask(objectId)
                return ResponseEntity.ok().build()
            }
        }
        return ResponseEntity.badRequest().build()
    }

    @PutMapping("/{id}")
    fun editTask(@PathVariable id: String?, @RequestBody task: Task): ResponseEntity<Unit> {
        if (id != null && ObjectId.isValid(id)) {
            val objectId = ObjectId(id)
            if (taskService.getTaskById(objectId).isPresent) {
                taskService.editTask(objectId, task)
                return ResponseEntity.ok().build()
            }
        }
        return ResponseEntity.badRequest().build()
    }
}