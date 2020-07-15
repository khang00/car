package khang.revive.car.service

import khang.revive.car.Repository.CarRepository
import khang.revive.car.Repository.ContractRepository
import khang.revive.car.Repository.EmployeeRepository
import khang.revive.car.Repository.UserRepository
import khang.revive.car.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarRenting @Autowired constructor(val carRepository: CarRepository,
                                        val contractRepository: ContractRepository,
                                        val userRepository: UserRepository,
                                        val employeeRepository: EmployeeRepository) {
    fun deleteAll() {
        carRepository.deleteAll()
        contractRepository.deleteAll()
        userRepository.deleteAll()
        employeeRepository.deleteAll()
    }

    fun getAllCar(): List<Car> {
        return carRepository.findAll()
    }

    fun createCar(car: Car): Car {
        return carRepository.save(car)
    }

    fun createEmployee(employee: Employee): Employee {
        return employeeRepository.save(employee)
    }

    fun getEmployeeById(id: String): Optional<Employee> {
        return employeeRepository.findById(id)
    }

    fun approveRentingContract(sale: Employee, contract: Contract): Optional<Contract> {
        return getEmployeeById(sale.id).flatMap { existedSale ->
            getCarById(contract.car).map { updateCarStatus(it, CarStatus.RENTING) }
                    .map { contractRepository.save(contract.copy(sale = existedSale.id)) }
        }
    }

    fun rentCar(renter: User, car: Car): Optional<Contract> {
        return getCarById(car.id).map { existedCar ->
            updateCarStatus(existedCar, CarStatus.WAITING)
            getUserById(renter.id).ifPresentOrElse({}, { createUser(renter) })
            contractRepository.save(Contract(renter = renter.id, car = existedCar.id))
        }
    }

    fun getCarById(id: String): Optional<Car> {
        return carRepository.findById(id)
    }

    fun getUserById(id: String): Optional<User> {
        return userRepository.findById(id)
    }

    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun updateCarStatus(car: Car, status: CarStatus): Car {
        return carRepository.save(car.copy(status = status))
    }
}