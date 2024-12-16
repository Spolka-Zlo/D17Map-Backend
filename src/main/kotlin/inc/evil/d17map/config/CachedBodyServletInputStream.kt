package inc.evil.d17map.config

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

private val logger = KotlinLogging.logger {}

class CachedBodyServletInputStream(cachedBody: ByteArray) : ServletInputStream() {

    private val cachedBodyInputStream: InputStream = ByteArrayInputStream(cachedBody)

    override fun isFinished(): Boolean =
        runCatching { cachedBodyInputStream.available() == 0 }
            .getOrElse {
                logger.error(it) { "Error while checking if the stream is finished: $it" }
                false
            }

    override fun isReady(): Boolean {
        return true
    }

    override fun setReadListener(readListener: ReadListener) {
        throw UnsupportedOperationException()
    }

    @Throws(IOException::class)
    override fun read(): Int {
        return cachedBodyInputStream.read()
    }
}
