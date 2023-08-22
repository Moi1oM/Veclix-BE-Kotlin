package paxHumana.veclix.users.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import paxHumana.veclix.users.service.UserResponse
import paxHumana.veclix.users.service.UserService


@RequestMapping("/users")
@RestController
class UserController (
    private val userService: UserService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun createUser(@RequestBody @Validated userRequest: UserRequestDto) : ResponseEntity<UserResponse> {
        log.info("$userRequest")
        return ok(userService.create(userRequest))
    }

    @GetMapping("/{id}")
    fun getUserWithId(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return ok(userService.findById(id))
    }

    @GetMapping("/all")
    fun getAllUser(): ResponseEntity<List<UserResponse>> {
        return ok(userService.findAllDesc())
    }

    @GetMapping
    fun findUserWithEmail(@RequestParam email: String): ResponseEntity<UserResponse> {
        return ok(userService.findByEmail(email))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody requestDto: UserRequestDto): ResponseEntity<UserResponse> {
        return ok(userService.update(id, requestDto))
    }


    @DeleteMapping("/{id}")
    fun deleteUserWithId(@PathVariable id: Long): ResponseEntity<Long> {
        userService.delete(id)
        return ok(id)
    }
}


data class UserRequestDto(
    val email: String,
    val username: String?,
    val avatarUrl: String?,
    val discordId: String?,
    val vcoin: Int?,
)
