package dev.luis.TimeToDo.Repository

import dev.luis.TimeToDo.Model.Task
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface TaskRepository : MongoRepository<Task, UUID> {
    fun getTaskByName(name: String): Optional<Task>
    fun getTaskById(id: ObjectId): Optional<Task>
}