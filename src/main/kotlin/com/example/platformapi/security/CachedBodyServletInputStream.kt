package com.example.platformapi.security

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import java.io.ByteArrayInputStream

class CachedBodyServletInputStream(private val cachedBody: ByteArray) : ServletInputStream() {

    private val inputStream = ByteArrayInputStream(cachedBody)

    override fun read(): Int = inputStream.read()

    override fun isFinished(): Boolean = inputStream.available() == 0

    override fun isReady(): Boolean = true

    override fun setReadListener(readListener: ReadListener) {
        throw RuntimeException("Not implemented")
    }
}
