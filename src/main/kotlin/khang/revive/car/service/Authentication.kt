package khang.revive.car.service

import khang.revive.car.Repository.EmployeeRepository
import khang.revive.car.model.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Authentication @Autowired constructor(private val employeeRepository: EmployeeRepository) {
    fun authenticate(employee: Employee): Boolean {
        return employeeRepository.findByAccountAndPassword(employee.account, employee.password)
                .map { true }
                .orElse(false)
    }
}