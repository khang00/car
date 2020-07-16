package khang.revive.car.service

import khang.revive.car.model.CarStatus
import khang.revive.car.model.Contract

interface ContractService {
    fun findByCarStatus(status :CarStatus): List<Contract>
}