package com.example.osivtest.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Statistics(
    date: LocalDate,
    userId: UUID,
    orderCount: Int,
    itemCount: Int,
) {
    @Id
    @Column(columnDefinition = "uuid")
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    val date: LocalDate = date

    @Column(columnDefinition = "uuid", nullable = false)
    val userId: UUID = userId

    @Column(nullable = false)
    val orderCount: Int = orderCount

    @Column(nullable = false)
    val itemCount: Int = itemCount
}
