package khang.revive.car.service

import khang.revive.car.Repository.EmployeeRepository
import khang.revive.car.model.Employee
import khang.revive.car.model.EmployeeRole
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthenticationSuite @Autowired constructor(
        private val employeeRepository: EmployeeRepository,
        private val authentication: Authentication) {

    val sale = Employee(role = EmployeeRole.SALE, name = "khang1", account = "khang01", password = "1")

    @BeforeEach
    fun populateDatabase() {
        employeeRepository.save(sale)
    }

    @AfterEach
    fun deleteDatabase() {
        employeeRepository.deleteAll()
    }

    @Test
    fun authenticationSuccess() {
        assert(authentication.authenticate(sale))
    }

    @Test
    fun authenticationFail() {
        val another = Employee(role = EmployeeRole.SALE, name = "khang1", account = "khang01", password = "0")
        val other = Employee(role = EmployeeRole.SALE, name = "khang1", account = "khang", password = "1")

        assert(!authentication.authenticate(another))
        assert(!authentication.authenticate(other))
    }
}