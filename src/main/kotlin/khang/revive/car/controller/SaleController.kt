package khang.revive.car.controller

import khang.revive.car.Repository.ContractRepository
import khang.revive.car.model.Contract
import khang.revive.car.model.Employee
import khang.revive.car.service.RentingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/sale")
@RestController
class SaleController @Autowired constructor(private val rentingService: RentingService,
                                            private val contractRepository: ContractRepository) {

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
}