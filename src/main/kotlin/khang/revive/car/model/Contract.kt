package khang.revive.car.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Contract(@Id val id: String = ObjectId.get().toHexString(),
                    val renter: String,
                    val car: String,
                    val sale: String = "",
                    val maintainer: String = "",
                    val maintenanceDetails: String = "",
                    val fine: Long = 0,
                    val totalFee: Long = 0,
                    val createDate: LocalDate = LocalDate.now())