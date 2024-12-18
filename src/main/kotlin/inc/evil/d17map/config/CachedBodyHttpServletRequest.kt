package inc.evil.d17map.config

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.springframework.util.StreamUtils
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

private val logger = KotlinLogging.logger {}

class CachedBodyHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

    private val cachedBody: ByteArray = try {
        val requestInputStream = request.inputStream
        StreamUtils.copyToByteArray(requestInputStream)

    } catch (ex: IOException) {
        logger.error(ex) { "Unable to read and cache request body: $ex" }
        throw IllegalStateException("Unable to read and cache request body", ex)
    }

    override fun getInputStream(): ServletInputStream {
        return CachedBodyServletInputStream(cachedBody)
    }

    override fun getReader(): BufferedReader {
        val byteArrayInputStream = ByteArrayInputStream(cachedBody)
        return BufferedReader(InputStreamReader(byteArrayInputStream))
    }
}
