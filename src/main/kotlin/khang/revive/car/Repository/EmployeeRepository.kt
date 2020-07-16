package khang.revive.car.Repository

import khang.revive.car.model.Employee
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmployeeRepository : MongoRepository<Employee, String> {
    fun findByAccountAndPassword(account: String, password: String): Optional<Employee>
    fun findByAccount(account: String): Optional<Employee>
}