package khang.revive.car.controller

import khang.revive.car.model.Employee
import khang.revive.car.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/authenticate")
@RestController
class AuthenticationController @Autowired constructor(private val authenticationService: AuthenticationService) {
    @PostMapping
    fun login(@RequestBody employee: Employee): ResponseEntity<String> {
        return if (authenticationService.authenticate(employee)) {
            ResponseEntity.ok(employee.account)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}