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

    fun approveRentingContract(sale: Employee, contract: Contract): Optional<Contract> {
        return employeeRepository.findById(sale.id).flatMap { existedSale ->
            carRepository.findById(contract.car).map { updateCarStatus(it, CarStatus.RENTING) }
                    .map { contractRepository.save(contract.copy(sale = existedSale.id)) }
        }
    }

    fun rentCar(renter: User, car: Car): Optional<Contract> {
        return carRepository.findById(car.id).map { existedCar ->
            updateCarStatus(existedCar, CarStatus.WAITING)
            userRepository.findById(renter.id).ifPresentOrElse({}, { userRepository.save(renter) })
            contractRepository.save(Contract(renter = renter.id, car = existedCar.id))
        }
    }

    fun updateCarStatus(car: Car, status: CarStatus): Car {
        return carRepository.save(car.copy(status = status))
    }
}