package khang.revive.car.service

import khang.revive.car.model.Car
import khang.revive.car.model.Contract
import khang.revive.car.model.Employee
import khang.revive.car.model.User
import java.util.*

interface RentingService {
    fun rentCar(renter: User, car: Car): Optional<Contract>
    fun approveRentingContract(sale: Employee, contract: Contract): Optional<Contract>
    fun makePayment(maintainer: Employee, contract: Contract): Optional<Contract>
    fun finishMaintenance(contract: Contract, maintenanceDetails: String): Optional<Contract>
}