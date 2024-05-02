package dev.luis.TimeToDo

import dev.luis.TimeToDo.Model.Task
import dev.luis.TimeToDo.Model.TaskStatus
import dev.luis.TimeToDo.Repository.TaskRepository
import dev.luis.TimeToDo.Service.TaskService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
class TaskServiceTests {

    @Autowired
    private lateinit var taskService: TaskService

    @MockBean
    private lateinit var taskRepository: TaskRepository

    @Test
    fun `createTask should return new task when valid inputs are provided`() {
        val name = "New Task"
        val status = TaskStatus.TODO
        val description = "New Description"
        val task = Task(name, status, description)

        Mockito.`when`(taskRepository.insert(ArgumentMatchers.any(Task::class.java))).thenReturn(task)

        val result = taskService.createTask("New Task", TaskStatus.TODO, "New Description")

        Assertions.assertEquals(task, result)
    }

    @Test
    fun `createTask should throw TaskException when a task is not correctly defined`() {
        Mockito.`when`(taskRepository.insert(Mockito.any(Task::class.java))).thenThrow(RuntimeException::class.java)

        Assertions.assertThrows(TaskException::class.java) {
            taskService.createTask("New Task", TaskStatus.TODO, "New Description")
        }
    }


    @Test
    fun `getAllTasks should return all tasks when tasks exist`() {
        val tasks = listOf(Task("Task1", TaskStatus.DOING, "TaskDescription1"), Task("Task2", TaskStatus.DONE, "TaskDescription2"))
        Mockito.`when`(taskRepository.findAll()).thenReturn(tasks)

        val result = taskService.getAllTasks()

        Assertions.assertEquals(tasks, result)
    }

    @Test
    fun `getTaskByName should return task when task with name exists`() {
        val task = Task("New Task", TaskStatus.TODO, "New Description")
        Mockito.`when`(taskRepository.getTaskByName("New Task")).thenReturn(Optional.of(task))

        val result = taskService.getTaskByName("New Task")

        Assertions.assertTrue(result.isPresent)
        Assertions.assertEquals(task, result.get())
    }

    @Test
    fun `getTaskById should return task when task with id exists`() {
        val id = ObjectId.get()
        val task = Task("New Task", TaskStatus.DONE, "New Description")
        Mockito.`when`(taskRepository.getTaskById(id)).thenReturn(Optional.of(task))

        val result = taskService.getTaskById(id)

        Assertions.assertTrue(result.isPresent)
        Assertions.assertEquals(task, result.get())
    }

    @Test
    fun `deleteTask should not throw exception when task with id exists`() {
        val id = ObjectId.get()
        val task = Task(id.toHexString(), "New Task", TaskStatus.TODO, "New Description")
        Mockito.`when`(taskRepository.getTaskById(id)).thenReturn(Optional.of(task))

        Assertions.assertDoesNotThrow { taskService.deleteTask(id) }
    }

    @Test
    fun `editTask should not throw exception when task with id exists`() {
        val id = ObjectId.get()
        val task = Task(id.toHexString(), "New Task", TaskStatus.DOING, "New Description")
        Mockito.`when`(taskRepository.getTaskById(id)).thenReturn(Optional.of(task))

        Assertions.assertDoesNotThrow { taskService.editTask(id, task) }
    }
}