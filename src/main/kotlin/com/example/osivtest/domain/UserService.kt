package com.example.osivtest.domain

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun create(command: CreateUserCommand): User {
        val user = User(command.name, command.age)
        return userRepository.save(user)
    }

    @Transactional
    fun order(userId: UUID, command: CreateOrderCommand): Order {
        val user = userRepository.findById(userId).orElseThrow()
        val items = command.items.map { Item(it.name) }
        val order = Order(items, user)
        user.order(order)
        return order
    }

    @Transactional(readOnly = true)
    fun getById(id: UUID): User {
        return userRepository.findById(id).orElseThrow()
    }

    @Transactional(readOnly = true)
    fun getAll(): List<User> {
        return userRepository.findAll().toList()
    }
}

data class CreateUserCommand(
    val name: String,
    val age: Int,
)

data class CreateOrderCommand(
    val items: List<CreateItemCommand>,
)

data class CreateItemCommand(
    val name: String,
)
