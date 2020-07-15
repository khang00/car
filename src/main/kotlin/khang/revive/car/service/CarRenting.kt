package khang.revive.car.service

import khang.revive.car.Repository.CarRepository
import khang.revive.car.Repository.ContractRepository
import khang.revive.car.Repository.UserRepository
import khang.revive.car.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarRenting @Autowired constructor(val carRepository: CarRepository,
                                        val contractRepository: ContractRepository,
                                        val userRepository: UserRepository) {
    fun getAllCar(): List<Car> {
        return carRepository.findAll()
    }

    fun createCar(car: Car): Car {
        return carRepository.save(car)
    }

    fun approveRentingContract(sale: Employee, contract: Contract): Contract {
        getCarById(contract.car).ifPresent { updateCarStatus(it, CarStatus.RENTING) }
        return contractRepository.save(contract.copy(sale = sale.id))
    }

    fun rentCar(renter: User, car: Car): Optional<Contract> {
        return getCarById(car.id).map { existedCar ->
            getUserById(renter.id).ifPresentOrElse({}, { createUser(renter) })
            updateCarStatus(car, CarStatus.WAITING)
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