package inc.evil.d17map.security.authorization.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test")
    fun test(): String {
        return "test"
    }

    @PostMapping("/test")
    fun post(@RequestBody body: TestBody): TestBody {
        return TestBody("a", "b", "c", "d", "e")
    }
}