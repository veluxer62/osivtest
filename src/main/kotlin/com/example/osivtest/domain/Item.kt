package com.example.osivtest.domain

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Item(
    name: String,
) {
    @Id
    @Column(columnDefinition = "uuid")
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    val name: String = name
}
