package khang.revive.car.service

import khang.revive.car.model.Employee

interface AuthenticationService {
    fun authenticate(employee: Employee): Boolean
}