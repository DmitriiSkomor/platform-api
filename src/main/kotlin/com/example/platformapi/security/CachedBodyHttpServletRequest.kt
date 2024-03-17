package com.example.platformapi.security

import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

class CachedBodyHttpServletRequest(
    request: HttpServletRequest,
    private val cachedBody: ByteArray,
) : HttpServletRequestWrapper(request) {

    override fun getInputStream(): ServletInputStream {
        return CachedBodyServletInputStream(cachedBody)
    }

    override fun getReader(): BufferedReader {
        val inputStream = ByteArrayInputStream(cachedBody)
        return BufferedReader(InputStreamReader(inputStream))
    }
}
