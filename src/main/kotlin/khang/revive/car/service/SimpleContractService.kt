package khang.revive.car.service

import khang.revive.car.Repository.CarRepository
import khang.revive.car.Repository.ContractRepository
import khang.revive.car.model.CarStatus
import khang.revive.car.model.Contract
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SimpleContractService @Autowired constructor(private val carRepository: CarRepository,
                                                   private val contractRepository: ContractRepository) : ContractService {
    override fun findByCarStatus(status: CarStatus): List<Contract> {
        return carRepository.findAllByStatus(status)
                .map { contractRepository.findByCar(it.id) }
                .filter { it.isPresent }
                .map { it.get() }
    }

}