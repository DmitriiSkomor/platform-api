package com.example.platformapi.persistence.model

import jakarta.persistence.*

@Entity
class Balance(
    @Id
    val userId: String,
    @Column(nullable = false)
    val userNick: String,
    @Column(nullable = false)
    var amount: Long,
    @Column(nullable = false)
    val currency: String,
    @Column(nullable = false)
    val denomination: Int,
    @Column(nullable = false)
    val maxWin: Long,
)
