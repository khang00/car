package khang.revive.car.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Car(@Id val id: String = ObjectId.get().toHexString(),
               val ownerId: String = "",
               val status: CarStatus = CarStatus.AVAILABLE,
               val model: String = "",
               val brand: String = "",
               val color: String = "",
               val price: Long = 0,
               val location: String = "",
               val seat: String = "",
               val description: String = "",
               val postedDate: LocalDate = LocalDate.now(),
               val lastMaintenanceDate: LocalDate = LocalDate.now(),
               val imageUrls: List<String> = listOf("https://media.wired.com/photos/5d09594a62bcb0c9752779d9/master/w_2560%2Cc_limit/Transpo_G70_TA-518126.jpg")
)