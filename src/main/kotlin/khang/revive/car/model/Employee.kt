package khang.revive.car.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(@Id val id: String = ObjectId.get().toHexString())