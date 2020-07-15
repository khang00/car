package khang.revive.car.service

import khang.revive.car.model.Car
import khang.revive.car.model.CarStatus
import khang.revive.car.model.Employee
import khang.revive.car.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CarRentingSuite(@Autowired private val carRenting: CarRenting) {
    val car = Car(brand = "yamaha", color = "white", seat = "4")

    @BeforeEach
    fun populateDatabase() {
        carRenting.createCar(car)
    }

    @Test
    fun createCar() {
        val expected = Car()
        val car = carRenting.createCar(expected)
        assert(car == expected)
    }

    @Test
    fun getAllCar() {
        val cars = carRenting.getAllCar()
        val expected = listOf(car)
        println(cars)
        println(expected)
        assert(cars == expected)
    }

    @Test
    fun rentCar() {
        val renter = User()
        val rentingContract = carRenting.rentCar(renter, car)
        assert(rentingContract.isPresent)
        assert(rentingContract.get().car == car.id)
        assert(rentingContract.get().renter == renter.id)

        assertCarStatus(car.id, CarStatus.WAITING)
    }

    @Test
    fun approveRentingContract() {
        val renter = User()
        val rentingContract = carRenting.rentCar(renter, car)
        val sale = Employee(role = "sale")
        val approvedContract = carRenting.approveRentingContract(sale, rentingContract.get())

        assert(approvedContract == rentingContract.get().copy(sale = sale.id))
        assertCarStatus(car.id, CarStatus.RENTING)
    }

    fun assertCarStatus(carId: String, expectedStatus: CarStatus) {
        val updatedCar = carRenting.getCarById(carId)
        assert(updatedCar.isPresent)
        assert(expectedStatus == updatedCar.get().status)
    }
}