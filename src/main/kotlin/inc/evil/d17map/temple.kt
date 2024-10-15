package inc.evil.d17map

import org.springframework.data.jpa.repository.JpaRepository


fun <T, ID : Any> JpaRepository<T, ID>.findOne(id: ID): T? = findById(id).orElse(null)