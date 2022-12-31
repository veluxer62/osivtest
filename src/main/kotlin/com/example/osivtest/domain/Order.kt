package com.example.osivtest.domain

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "\"order\"")
class Order(
    items: List<Item>,
    orderer: User,
) {
    @Id
    @Column(columnDefinition = "uuid")
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "order_id", nullable = false)
    val items: List<Item> = items

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val orderer: User = orderer
}
