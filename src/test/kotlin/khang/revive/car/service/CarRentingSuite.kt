package khang.revive.car.service

import khang.revive.car.Repository.UserRepository
import khang.revive.car.model.Car
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.util.Assert

@SpringBootTest
class CarRentingSuite(@Autowired private val carRenting: CarRenting) {
    @Test
    fun createCar() {
        val expected = Car()
        val car = carRenting.createCar(expected)
        assert(car == expected)
    }

    @Test
    fun getAllCar() {
        val cars = carRenting.getAllCar()
        val expected = listOf<Car>()
        assert(cars == expected)
    }
}