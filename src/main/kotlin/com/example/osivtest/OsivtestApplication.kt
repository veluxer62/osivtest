package com.example.osivtest

import com.example.osivtest.domain.CreateItemCommand
import com.example.osivtest.domain.CreateOrderCommand
import com.example.osivtest.domain.CreateUserCommand
import com.example.osivtest.domain.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableScheduling
class OsivtestApplication

fun main(args: Array<String>) {
    runApplication<OsivtestApplication>(*args)
}

@Component
class StartUpCommand(
    private val userService: UserService,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val createUserCommand = CreateUserCommand("홍길동", 30)
        val user = userService.create(createUserCommand)

        val createItemCommands = listOf(
            CreateItemCommand("품목1"),
            CreateItemCommand("품목2"),
            CreateItemCommand("품목3"),
        )
        val createOrderCommand = CreateOrderCommand(createItemCommands)
        userService.order(user.id, createOrderCommand)

        val createUserCommand2 = CreateUserCommand("아무개", 32)
        val user2 = userService.create(createUserCommand2)

        val createItemCommands2 = listOf(
            CreateItemCommand("품목1"),
            CreateItemCommand("품목2"),
            CreateItemCommand("품목3"),
        )
        val createOrderCommand2 = CreateOrderCommand(createItemCommands2)
        userService.order(user2.id, createOrderCommand2)

        val createItemCommands3 = listOf(
            CreateItemCommand("품목11"),
            CreateItemCommand("품목22"),
            CreateItemCommand("품목33"),
        )
        val createOrderCommand3 = CreateOrderCommand(createItemCommands3)
        userService.order(user2.id, createOrderCommand3)

        val createItemCommands4 = listOf(
            CreateItemCommand("품목111"),
            CreateItemCommand("품목222"),
            CreateItemCommand("품목333"),
        )
        val createOrderCommand4 = CreateOrderCommand(createItemCommands4)
        userService.order(user2.id, createOrderCommand4)
    }
}
