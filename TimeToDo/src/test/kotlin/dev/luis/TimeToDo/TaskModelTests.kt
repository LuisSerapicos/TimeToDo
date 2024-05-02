package dev.luis.TimeToDo

import dev.luis.TimeToDo.Model.Task
import dev.luis.TimeToDo.Model.TaskStatus
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TaskModelTests {

    @Test
    fun `task constructor should initialize fields correctly`() {
        val task = Task("Test Task", TaskStatus.TODO, "Test Description")

        assertEquals("Test Task", task.name)
        assertEquals(TaskStatus.TODO, task.status)
        assertEquals("Test Description", task.description)
    }

    @Test
    fun `task constructor should initialize id`() {
        val task = Task("Test Task", TaskStatus.TODO, "Test Description")

        assertNotNull(task.id)
    }

    @Test
    fun `task constructor should initialize fields correctly with id`() {
        val id = ObjectId().toHexString()
        val task = Task(id, "Test Task", TaskStatus.TODO, "Test Description")

        assertEquals(id, task.id)
        assertEquals("Test Task", task.name)
        assertEquals(TaskStatus.TODO, task.status)
        assertEquals("Test Description", task.description)
    }
}