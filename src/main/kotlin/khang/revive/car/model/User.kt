package khang.revive.car.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(@Id val id: String = ObjectId.get().toHexString(),
                val name: String = "",
                val phone: String = "",
                val address: String = "",
                val dob: String = "",
                val gender: String = "")