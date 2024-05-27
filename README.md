# 🔵 D17Map - Backend Side 🗺 🔍


## 🔷 How to run?
### 🔹 Setup
Before running any of the following commands, you should create a `.env` file
in the same directory as `docker-compose.yaml`. The `.env` file should
contain your password to the database:

#### .env
```text
PASSWORD=<your_password>
```

### 🔹 Commands
The following commands should be issued from the root directory of the project.

1. `docker compose build`
2. `docker compose up -d`


## 🔷 Cleaning Docker
Sometimes docker has problems with itself, but there is a couple of commands which you can try to fix it

1. `docker compose down` - this command stops containers and removes them.
2. `docker system prune` - removes all dangling/untagged resources
3. `docker system prune -a` - removes a little bit more ;)
4. `docker volume rm <your volume name>` - obvious (use `docker volume ls` to list all volumes)
5. `docker volume prune` - removes all dangling volumes

Other useful commands may be found [here](https://contabo.com/blog/how-to-remove-docker-volumes-images-and-containers/)



## Testing for future

```kotlin
package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class OrderRouteTests {
    @Test
    fun testGetOrder() = testApplication {
        val response = client.get("/order/2020-04-06-01")
        assertEquals(
            """{"number":"2020-04-06-01","contents":[{"item":"Ham Sandwich","amount":2,"price":5.5},{"item":"Water","amount":1,"price":1.5},{"item":"Beer","amount":3,"price":2.3},{"item":"Cheesecake","amount":1,"price":3.75}]}""",
            response.bodyAsText()
        )
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
```

## Object model

![Object Model](docs/object_model.png)


## Problems / TODO

- [ ] Fixing model / how to save object with a reference to other object
- [ ] Adjusting DAO layer
- [ ] Fix mappings
- [ ] Introduce exceptions to service layer
- [ ] Introduce status pages / exception handling in controller layer
