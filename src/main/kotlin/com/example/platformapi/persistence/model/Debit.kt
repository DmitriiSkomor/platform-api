package com.example.platformapi.persistence.model

import jakarta.persistence.*

@Entity
class Debit(
    @Id
    val betId: String,
    @Column(nullable = false)
    val userId: String,
    @Column(nullable = false)
    val amount: Long,
)
