package inc.evil.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object Event : Table() {
    val id = uuid("id").uniqueIndex()
    val name = text("name")
    val description = text("description")
    val startDate = datetime("start_date")
    val endDate = datetime("end_date")
}

