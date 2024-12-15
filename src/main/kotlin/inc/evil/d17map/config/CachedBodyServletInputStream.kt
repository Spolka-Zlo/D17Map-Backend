package inc.evil.d17map.config

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

class CachedBodyServletInputStream(cachedBody: ByteArray) : ServletInputStream() {

    private val cachedBodyInputStream: InputStream = ByteArrayInputStream(cachedBody)

    override fun isFinished(): Boolean {
        return try {
            cachedBodyInputStream.available() == 0
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
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
