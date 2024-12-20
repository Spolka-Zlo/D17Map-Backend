package inc.evil.d17map.web.filters

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

class CachedBodyHttpServletRequest @Throws(IOException::class) constructor(request: HttpServletRequest) :
    HttpServletRequestWrapper(request) {
    private val cachedBody: ByteArray

    init {
        logger.info { "Initializing cached request..." }
        cachedBody = StreamUtils.copyToByteArray(request.inputStream)
        logger.info { "Initializing cached request completed." }
    }

    override fun getInputStream(): ServletInputStream {
        return CachedBodyServletInputStream(cachedBody)
    }

    override fun getReader(): BufferedReader {
        val byteArrayInputStream = ByteArrayInputStream(cachedBody)
        return BufferedReader(InputStreamReader(byteArrayInputStream))
    }
}