package com.example.platformapi.persistence.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class SessionInfo(
    @Id
    val id: String,
    @Column(nullable = false)
    val userId: String,
)
