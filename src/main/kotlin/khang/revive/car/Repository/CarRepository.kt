package khang.revive.car.Repository

import khang.revive.car.model.Car
import khang.revive.car.model.CarStatus
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CarRepository: MongoRepository<Car, String> {
    fun findAllByStatus(status: CarStatus) :List<Car>
}