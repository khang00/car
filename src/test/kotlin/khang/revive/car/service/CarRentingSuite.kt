package khang.revive.car.service

import khang.revive.car.Repository.CarRepository
import khang.revive.car.Repository.EmployeeRepository
import khang.revive.car.Repository.UserRepository
import khang.revive.car.model.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CarRentingSuite @Autowired constructor(private val carRenting: CarRenting,
                                             private val carRepository: CarRepository,
                                             private val userRepository: UserRepository,
                                             private val employeeRepository: EmployeeRepository) {

    val car = Car(brand = "yamaha", color = "white", seat = "4")
    val sale = Employee(role = EmployeeRole.SALE, name = "khang1", account = "khang01", password = "1")
    val maintainer = Employee(role = EmployeeRole.MAINTAINER, name = "khang2", account = "khang02", password = "2")

    @BeforeEach
    fun populateDatabase() {
        carRepository.save(car)
        employeeRepository.save(sale)
        employeeRepository.save(maintainer)
    }

    @AfterEach
    fun deleteDatabase() {
        carRenting.deleteAll()
    }

    @Test
    fun rentCar() {
        //given
        val renter = User()
        val rentingContract = carRenting.rentCar(renter, car)

        // Renting contract should be successfully established
        assert(rentingContract.isPresent)
        assert(rentingContract.get().car == car.id)
        assert(rentingContract.get().renter == renter.id)

        // Car status be changed to waiting for approval
        assertCarStatus(car.id, CarStatus.WAITING)

        // User should be created if they have not been created
        assert(userRepository.findById(renter.id).isPresent)
    }

    @Test
    fun approveRentingContract() {
        // Given
        val renter = User()
        val rentingContract = carRenting.rentCar(renter, car)
        val approvedContract = carRenting.approveRentingContract(sale, rentingContract.get())

        // Employee should be existed
        assert(employeeRepository.findById(sale.id).isPresent)

        // Contract should contains information of the employee who approved the contract
        assert(approvedContract.get() == rentingContract.get().copy(sale = sale.id))

        // Car status should be changed to renting
        assertCarStatus(car.id, CarStatus.RENTING)
    }

    @Test
    fun approveRentingContractWithNonExistedEmployee() {
        // Given
        val renter = User()
        val nonExistedEmployee = Employee(role = EmployeeRole.SALE, name = "", account = "", password = "")
        val rentingContract = carRenting.rentCar(renter, car)
        val approvedContract = carRenting.approveRentingContract(nonExistedEmployee, rentingContract.get())

        //contract should be empty
        assert(approvedContract.isEmpty)
    }

    @Test
    fun transferToMaintenance() {
        // Given
        val renter = User()
        val rentingContract = carRenting.rentCar(renter, car)
        val approvedContract = carRenting.approveRentingContract(sale, rentingContract.get())
        val maintenanceContract = carRenting.makePayment(maintainer, approvedContract.get())

        // Employee should be existed
        assert(employeeRepository.findById(maintainer.id).isPresent)

        // Contract should contains information of the employee who maintain the car mentioned in the contract
        assert(maintenanceContract.get() == maintenanceContract.get().copy(maintainer = maintainer.id))

        // Car status should be changed to renting
        assertCarStatus(car.id, CarStatus.MAINTENANCE)
    }

    @Test
    fun transferToMaintenanceWithNonExistedEmployee() {
        // Given
        val renter = User()
        val nonExistedEmployee = Employee(role = EmployeeRole.MAINTAINER, name = "", account = "", password = "")
        val rentingContract = carRenting.rentCar(renter, car)
        val approvedContract = carRenting.approveRentingContract(sale, rentingContract.get())
        val maintenanceContract = carRenting.makePayment(nonExistedEmployee, approvedContract.get())

        //contract should be empty
        assert(maintenanceContract.isEmpty)
    }

    @Test
    fun finishMaintenance() {
        // Given
        val renter = User()
        val rentingContract = carRenting.rentCar(renter, car)
        val approvedContract = carRenting.approveRentingContract(sale, rentingContract.get())
        val maintenanceContract = carRenting.makePayment(maintainer, approvedContract.get())
        val endContract = carRenting.finishMaintenance(maintenanceContract.get(), "oke")

        // Employee should be existed
        assert(employeeRepository.findById(maintainer.id).isPresent)

        // Contract should contains the maintenance details
        assert(endContract.get() == endContract.get().copy(maintenanceDetails = "oke"))

        // Car status should be changed to renting
        assertCarStatus(car.id, CarStatus.AVAILABLE)
    }

    @Test
    fun updateCarStatus() {
        carRenting.updateCarStatus(car, CarStatus.WAITING)
        assertCarStatus(car.id, CarStatus.WAITING)

        carRenting.updateCarStatus(car, CarStatus.RENTING)
        assertCarStatus(car.id, CarStatus.RENTING)

        carRenting.updateCarStatus(car, CarStatus.MAINTENANCE)
        assertCarStatus(car.id, CarStatus.MAINTENANCE)
    }

    @Test
    fun getAllCar() {
        val cars = carRepository.findAll()
        val expected = listOf(car)
        assert(cars == expected)
    }

    fun assertCarStatus(carId: String, expectedStatus: CarStatus) {
        val updatedCar = carRepository.findById(carId)
        assert(updatedCar.isPresent)
        assert(expectedStatus == updatedCar.get().status)
    }
}