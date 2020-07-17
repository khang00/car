package khang.revive.car.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(@Id val id: String = ObjectId.get().toHexString(),
                    val role: EmployeeRole,
                    val name: String,
                    val account: String,
                    val password: String = "",
                    val imageUrls: List<String> = listOf("https://www.shareicon.net/data/128x128/2016/05/24/770137_man_512x512.png"))