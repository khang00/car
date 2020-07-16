package khang.revive.car.Repository

import khang.revive.car.model.Contract
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ContractRepository : MongoRepository<Contract, String> {
    fun findByCar(car: String): Optional<Contract>
    fun findAllBySale(sale: String): List<Contract>
    fun findAllByMaintainer(maintainer: String): List<Contract>
}