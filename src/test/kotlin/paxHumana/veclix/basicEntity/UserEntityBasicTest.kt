package paxHumana.veclix.basicEntity

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import paxHumana.veclix.users.controller.UserRequestDto
import paxHumana.veclix.users.domain.User
import paxHumana.veclix.users.repository.UserRepository
import paxHumana.veclix.users.service.UserService

class UserEntityBasicTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val userService = UserService(userRepository)

    Given("특정 회원이 가입하려고 할 때") {
        val userRequest = UserRequestDto(
            email = "test@example.com",
            username = "testUser",
            avatarUrl = "example.png",
            discordId = "14325",
            vcoin = 0
        )
        val savedUser = User(
            id = 1L,
            email = "test@example.com",
            username = "testUser",
            avatarUrl = "example.png",
            discordId = "14325",
            vcoin = 0
        )

        When("이메일이 중복되지 않는 경우") {
            every { userRepository.existsByEmail(userRequest.email) } returns false
            every { userRepository.save(any()) } returns savedUser

            val result = userService.create(userRequest)

            Then("회원 가입이 성공적으로 완료된다") {
                result.email shouldBe userRequest.email
                result.username shouldBe userRequest.username
                // You can add more assertions here...
            }
        }

        When("이메일이 중복되는 경우") {
            every { userRepository.existsByEmail(userRequest.email) } returns true

            Then("IllegalStateException이 터져야 한다.") {
                shouldThrow<IllegalArgumentException> {
                    userService.create(userRequest)
                }
            }
        }
    }

    afterTest {
        clearMocks(userRepository)
    }
})
