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
    var id: ObjectId? = null
    var name: String? = null
    var status: String? = null

    constructor(name: String, status: String) : this() {
        this.name = name
        this.status = status
    }

    constructor()
}
