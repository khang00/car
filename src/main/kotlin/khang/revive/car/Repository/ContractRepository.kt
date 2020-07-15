package khang.revive.car.Repository

import khang.revive.car.model.Contract
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractRepository: MongoRepository<Contract, String>