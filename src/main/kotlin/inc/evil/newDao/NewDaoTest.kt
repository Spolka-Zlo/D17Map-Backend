package inc.evil.newDao

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID


object StarWarsFilms : IntIdTable() {
    val sequelId = integer("sequel_id").uniqueIndex()
    val name = varchar("name", 50)
    val director = varchar("director", 50)
}

class StarWarsFilm(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, StarWarsFilm>(StarWarsFilms)
    var sequelId by StarWarsFilms.sequelId
    var name     by StarWarsFilms.name
    var director by StarWarsFilms.director
}

object Users: IntIdTable() {
    val name = varchar("name", 50)
}


class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    var name      by Users.name
}

object UserRatings: UUIDTable() {
    val value = long("value")
    val film = reference("film", StarWarsFilms)
    val user = reference("user", Users)
}



class UserRating(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<UserRating>(UserRatings)

    var value by UserRatings.value
    var film  by StarWarsFilm referencedOn UserRatings.film
    var user  by User         referencedOn UserRatings.user
}