package khang.revive.car.controller

import khang.revive.car.Repository.ContractRepository
import khang.revive.car.Repository.EmployeeRepository
import khang.revive.car.model.CarStatus
import khang.revive.car.model.Contract
import khang.revive.car.model.Employee
import khang.revive.car.service.ContractService
import khang.revive.car.service.RentingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/sale")
@RestController
class SaleController @Autowired constructor(private val rentingService: RentingService,
                                            private val contractRepository: ContractRepository,
                                            private val contractService: ContractService,
                                            private val employeeRepository: EmployeeRepository) {

    data class SaleRequest(val sale: Employee, val contract: String)

    @PutMapping("/contract/approve")
    fun approveContract(@RequestBody request: SaleRequest): ResponseEntity<Contract> {
        return contractRepository.findById(request.contract)
                .flatMap { rentingService.approveRentingContract(request.sale, it) }
                .map { ResponseEntity.ok(it) }
                .orElse(ResponseEntity.badRequest().build())
    }

    @PutMapping("/contract/payment")
    fun makePayment(@RequestBody request: SaleRequest): ResponseEntity<Contract> {
        return contractRepository.findById(request.contract)
                .flatMap { rentingService.makePayment(request.sale, it) }
                .map { ResponseEntity.ok(it) }
                .orElse(ResponseEntity.badRequest().build())
    }

    @GetMapping("/contract/waiting")
    fun findWaitingContract(): ResponseEntity<List<Contract>> {
        return contractService.findByCarStatus(CarStatus.WAITING)
                .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/contract/approved")
    fun findApprovedContract(): ResponseEntity<List<Contract>> {
        return contractService.findByCarStatus(CarStatus.RENTING)
                .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/contract/approvedByAccount")
    fun findApprovedContractByAccount(@RequestParam account: String): ResponseEntity<List<Contract>> {
        return contractService.findByCarStatus(CarStatus.RENTING)
                .filter { contract -> employeeRepository.findByAccount(account)
                        .map { contract.sale == it.id }
                        .orElse(false)
                }
                .let { ResponseEntity.ok(it) }
    }
}