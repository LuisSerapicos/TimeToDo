package dev.luis.TimeToDo.Model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
class Task {
    @Id
    var id: String? = ObjectId().toHexString()
        set(value) {
            if (value.isNullOrBlank())
                throw IllegalArgumentException("Id cannot be blank")
            else if (!ObjectId.isValid(value))
                throw IllegalArgumentException("Id is not valid")
            else
                field = value
        }

    var name: String? = null
        set(value) {
            if (value.isNullOrBlank())
                throw IllegalArgumentException("Name cannot be blank")
            else if (value.length < 3 || value.length > 25)
                throw IllegalArgumentException("Name should have between 3 and 25 characters")
            else
                field = value
        }

    var status: TaskStatus? = null
        set(value) {
            if (value == null)
                throw IllegalArgumentException("Status cannot be null")
            else if (value != TaskStatus.TODO && value != TaskStatus.DOING && value != TaskStatus.DONE)
                throw IllegalArgumentException("Status should be either TODO, DOING or DONE")
            else
                field = value
        }

    var description: String? = null
        set(value) {
            if (value.isNullOrBlank())
                throw IllegalArgumentException("Description cannot be blank")
            else if (value.length < 3 || value.length > 150)
                throw IllegalArgumentException("Description should be between 3 and 150 characters")
            else
                field = value
        }

    constructor(name: String, status: TaskStatus, description: String) : this() {
        this.name = name
        this.status = status
        this.description = description
    }

    constructor()

    constructor(id: String, name: String, status: TaskStatus, description: String) : this() {
        this.id = id
        this.name = name
        this.status = status
        this.description = description
    }
}
