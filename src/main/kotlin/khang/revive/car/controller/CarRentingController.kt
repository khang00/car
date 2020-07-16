package khang.revive.car.controller

import khang.revive.car.Repository.CarRepository
import khang.revive.car.model.Car
import khang.revive.car.model.CarStatus
import khang.revive.car.model.Contract
import khang.revive.car.model.User
import khang.revive.car.service.RentingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("renting")
@RestController
class CarRentingController @Autowired constructor(private val rentingService: RentingService,
                                                  private val carRepository: CarRepository) {
    data class RentRequest(val car: Car, val renter: User)

    @PostMapping
    fun rentCar(@RequestBody rentRequest: RentRequest): ResponseEntity<Contract> {
        return rentingService.rentCar(rentRequest.renter, rentRequest.car)
                .map { ResponseEntity.ok(it) }
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build())
    }

    @GetMapping("/car")
    fun findCarById(@RequestParam id: String): ResponseEntity<Car> {
        return carRepository.findById(id).map { ResponseEntity.ok(it) }
                .orElse(ResponseEntity.notFound().build())
    }

    @GetMapping("/car/available")
    fun findAvailableCar(): ResponseEntity<List<Car>> {
        return ResponseEntity.status(HttpStatus.OK).body(carRepository.findAllByStatus(CarStatus.AVAILABLE))
    }
}