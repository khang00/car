package khang.revive.car.controller

import khang.revive.car.Repository.CarRepository
import khang.revive.car.Repository.EmployeeRepository
import khang.revive.car.model.Car
import khang.revive.car.model.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/manager")
@RestController
class ManagerController @Autowired constructor(private val carRepository: CarRepository,
                                               private val employeeRepository: EmployeeRepository) {

    @PostMapping("/employee")
    fun createEmployee(@RequestBody employee: Employee): ResponseEntity<Employee> {
        return ResponseEntity.ok(employeeRepository.save(employee))
    }

    @GetMapping("/employee")
    fun getAllEmployee(): ResponseEntity<List<Employee>> {
        return ResponseEntity.ok(employeeRepository.findAll())
    }

    @PostMapping("/car")
    fun createCar(@RequestBody car: Car): ResponseEntity<Car> {
        return ResponseEntity.ok(carRepository.save(car))
    }

    @GetMapping("/car")
    fun getAllCar(): ResponseEntity<List<Car>> {
        return ResponseEntity.ok(carRepository.findAll())
    }
}