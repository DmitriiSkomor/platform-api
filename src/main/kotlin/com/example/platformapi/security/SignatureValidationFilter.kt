package com.example.platformapi.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.DigestUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import java.io.BufferedReader

@Component
@Order(1)
class SignatureValidationFilter(
    @Value("\${security.secret-key}")
    private val secretKey: String,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val cachingRequest = ContentCachingRequestWrapper(request)
        val body = cachingRequest.inputStream.bufferedReader().use(BufferedReader::readText)
        val clientSignature = cachingRequest.getHeader("Sign")
        if (validateSignature(body.replace("\\s+".toRegex(), ""), clientSignature)) {
            val cachedBodyRequest = CachedBodyHttpServletRequest(cachingRequest, body.toByteArray(Charsets.UTF_8))
            filterChain.doFilter(cachedBodyRequest, response)
        } else {
            if (!response.isCommitted) {
                response.status = HttpStatus.UNAUTHORIZED.value()
                response.contentType = "text/plain;charset=UTF-8"
                response.writer.write("Invalid signature")
            }
        }
    }

    private fun validateSignature(body: String, clientSignature: String?): Boolean {
        return clientSignature != null &&
            DigestUtils.md5DigestAsHex((body + secretKey).toByteArray()) == clientSignature
    }
}
