package khang.revive.car.controller

import khang.revive.car.Repository.ContractRepository
import khang.revive.car.Repository.EmployeeRepository
import khang.revive.car.model.Contract
import khang.revive.car.service.RentingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/maintainer")
@RestController
class MaintainerController @Autowired constructor(private val rentingService: RentingService,
                                                  private val contractRepository: ContractRepository,
                                                  private val employeeRepository: EmployeeRepository) {

    data class FinishedMaintenanceRequest(val contract: String, val details: String, val fine: Long)

    @PutMapping("/contract/finished")
    fun finishMaintenance(@RequestBody request: FinishedMaintenanceRequest): ResponseEntity<Contract> {
        return contractRepository.findById(request.contract)
                .flatMap { rentingService.finishMaintenance(it, request.details) }
                .map { ResponseEntity.ok(it) }
                .orElse(ResponseEntity.badRequest().build())
    }

    @GetMapping("/contract")
    fun findAllContractByAccount(@RequestParam account: String): ResponseEntity<List<Contract>> {
        return employeeRepository.findByAccount(account)
                .map { contractRepository.findAllByMaintainer(it.id) }
                .map { ResponseEntity.ok(it) }
                .orElse(ResponseEntity.badRequest().build())
    }
}