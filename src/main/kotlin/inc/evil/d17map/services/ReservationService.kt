package inc.evil.d17map.services

import inc.evil.d17map.dtos.ReservationRequest
import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.dtos.ReservationUpdateRequest
import inc.evil.d17map.entities.Reservation
import inc.evil.d17map.entities.User
import inc.evil.d17map.enums.RecurringType
import inc.evil.d17map.enums.RecurringType.*
import inc.evil.d17map.enums.ReservationType
import inc.evil.d17map.exceptions.*
import inc.evil.d17map.findOne
import inc.evil.d17map.mappers.toReservationResponse
import inc.evil.d17map.repositories.BuildingRepository
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.ReservationRepository
import inc.evil.d17map.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val classroomRepository: ClassroomRepository,
    private val userRepository: UserRepository,
    private val buildingRepository: BuildingRepository,
    private val classroomService: ClassroomService
) {
    fun getGivenDayReservations(date: LocalDate, buildingName: String): List<ReservationResponse> {
        val reservations = reservationRepository.findAllByDateAndBuildingName(date, buildingName)
        return reservations.map { toReservationResponse(it) }
    }

    fun getReservationById(buildingName: String, id: UUID): ReservationResponse {
        val reservation = reservationRepository.findOne(id) ?: throw ReservationNotFoundException(id)

        if (reservation.classroom.floor.building.name != buildingName)
            throw InvalidReservationDataException("The building name '$buildingName' does not match the " +
                    "classroom's building '${reservation.classroom.floor.building.name}'.")

        return toReservationResponse(reservation)
    }

    fun createReservation(buildingName: String, reservationRequest: ReservationRequest): ReservationResponse {
        val classroom = classroomRepository.findOne(reservationRequest.classroomId)
            ?: throw ClassroomNotFoundException(reservationRequest.classroomId)

        val building = buildingRepository.findByName(buildingName)
            ?: throw BuildingNotFoundException(buildingName)

        if (classroom.floor.building.name != building.name) {
            throw InvalidReservationDataException("The building name '$buildingName' does not match the " +
                    "classroom's building '${classroom.floor.building.name}'.")
        }

        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        if (reservationRequest.startTime >= reservationRequest.endTime) {
            throw InvalidReservationDataException("Start time must be earlier than end time.")
        }

        if (reservationRequest.startTime.minute % 15 != 0 || reservationRequest.endTime.minute % 15 != 0) {
            throw InvalidReservationDataException(
                "Start time (${reservationRequest.startTime}) and end time (${reservationRequest.endTime}) must " +
                        "align with 15-minute time slots (e.g., 08:00, 08:15, 08:30)."
            )
        }

        val reservation = Reservation(
            title = reservationRequest.title,
            description = reservationRequest.description,
            date = reservationRequest.date,
            startTime = reservationRequest.startTime,
            endTime = reservationRequest.endTime,
            classroom = classroom,
            type = reservationRequest.type,
            user = user,
            numberOfParticipants = reservationRequest.numberOfParticipants
        )


        val savedReservation = reservationRepository.save(reservation)
        return toReservationResponse(savedReservation)
    }


    fun getReservationsForWeek(monday: LocalDate, buildingName: String): List<ReservationResponse> {
        val endOfWeek = monday.plusDays(6)
        val reservations = reservationRepository.findAllByDateBetweenAndBuildingName(monday, endOfWeek, buildingName)
        return reservations.map { toReservationResponse(it) }
    }

    fun getUserFutureReservations(buildingName: String): List<ReservationResponse>? {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        val futureReservations = reservationRepository.findAllFuture(
            user.id!!,
            LocalDate.now(),
            LocalTime.now(),
            buildingName
        )

        return futureReservations.map { toReservationResponse(it) }
    }


    fun getUserWeekReservations(monday: LocalDate, buildingName: String): List<ReservationResponse> {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        val endOfWeek = monday.plusDays(6)

        val reservations = reservationRepository.findAllByUserAndDateBetweenAndBuilding(user.id!!, monday, endOfWeek, buildingName)
        return reservations.map { toReservationResponse(it) }
    }

    fun getReservationTypes(): List<String> {
        return ReservationType.entries.map { it.value }
    }

    fun removeReservation(id: UUID, buildingName: String) {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { ReservationNotFoundException(id) }

        val classroom = reservation.classroom
        if (classroom.floor.building.name != buildingName) {
            throw InvalidReservationDataException("The classroom's building does not match the specified building '$buildingName'.")
        }
        reservationRepository.deleteById(id)
    }

    fun updateReservation(id: UUID, buildingName: String, updateRequest: ReservationUpdateRequest): ReservationResponse {
        val reservation = reservationRepository.findById(id).orElseThrow { ReservationNotFoundException(id) }

        val classroom = classroomRepository.findById(updateRequest.classroomId)
            .orElseThrow { ClassroomNotFoundException(updateRequest.classroomId) }

        if (classroom.floor.building.name != buildingName) {
            throw InvalidReservationDataException("The classroom's building does not match the specified building '$buildingName'.")
        }

        reservation.apply {
            this.title = updateRequest.title
            this.description = updateRequest.description
            this.type = updateRequest.type
            this.classroom = classroom
        }

        val updatedReservation = reservationRepository.save(reservation)
        return toReservationResponse(updatedReservation)
    }


    fun getCurrentOrFutureEvents(currentDateTime: LocalDateTime, buildingName: String): List<ReservationResponse> {
        val currentDate = currentDateTime.toLocalDate()
        val currentTime = currentDateTime.toLocalTime()

        val reservations = reservationRepository.findAllCurrentOrFutureEvents(currentDate, currentTime, buildingName)
        return reservations.map { toReservationResponse(it) }
    }

    fun updateReservationAdmin(id: UUID, buildingName: String, adminUpdateRequest: ReservationRequest): ReservationResponse {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { ReservationNotFoundException(id) }

        val classroom = classroomRepository.findById(adminUpdateRequest.classroomId)
            .orElseThrow { ClassroomNotFoundException(adminUpdateRequest.classroomId) }

        if (classroom.floor.building.name != buildingName) {
            throw InvalidReservationDataException("The classroom's building does not match the specified building '$buildingName'.")
        }

        reservation.apply {
            this.title = adminUpdateRequest.title
            this.description = adminUpdateRequest.description
            this.date = adminUpdateRequest.date
            this.startTime = adminUpdateRequest.startTime
            this.endTime = adminUpdateRequest.endTime
            this.classroom = classroom
            this.type = adminUpdateRequest.type
            this.numberOfParticipants = adminUpdateRequest.numberOfParticipants
        }
        val updatedReservation = reservationRepository.save(reservation)
        return toReservationResponse(updatedReservation)
    }


    fun getReservationsForUserInBuilding(userId: UUID, building: String): List<ReservationResponse> {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId.toString()) }

        val reservations = reservationRepository.findAllByUserIdAndBuildingName(user.id!!, building)
        return reservations.map { toReservationResponse(it) }
    }

    fun getAllReservationsForBuilding(buildingName: String): List<ReservationResponse> {
        val reservations = reservationRepository.findAllByBuildingName(buildingName)
        return reservations.map { toReservationResponse(it) }
    }

    fun getAllReservationsInCycle(buildingName: String, recurringId: UUID): List<ReservationResponse> {
        val reservations = reservationRepository.findAllByRecurringIdAndBuildingName(recurringId, buildingName)
        return reservations.map { toReservationResponse(it) }
    }

    fun removeUpcomingReservationsInCycle(buildingName: String, recurringId: UUID, removeOnlyUpcoming: Boolean) {
        val now = LocalDateTime.now()

        val reservations = if (removeOnlyUpcoming)
            reservationRepository.findAllByRecurringIdAndBuildingName(recurringId, buildingName)
                .filter { it.date.atTime(it.startTime) >= now }
            else reservationRepository.findAllByRecurringIdAndBuildingName(recurringId, buildingName)

        if (reservations.isEmpty()) {
            throw RecurringReservationNotFoundException(recurringId)
        }
        reservations.forEach { reservationRepository.delete(it) }
    }

    fun createRecurringReservation(buildingName: String, request: ReservationRequest): Map<String, Any> {
        val collisions = mutableListOf<LocalDate>()
        var current = request.date

        while (!current.isAfter(request.recurringEndDate!!)) {
            val collisionDates = reservationRepository.findCollisions(
                request.classroomId,
                current,
                request.startTime,
                request.endTime,
                buildingName = buildingName
            )

            if (collisionDates.isNotEmpty()) {
                collisions.add(current)
            }
            current = getNextReservationDate(current, request.recurringType!!)
        }

        if (collisions.isNotEmpty()) {
            val recurringId = createRecurringReservationWithoutCollisions(
                buildingName, request, collisions, ReservationType.BLOCKED
            )
            return mapOf(
                "message" to "Collisions detected.",
                "collisions" to collisions,
                "recurringId"  to recurringId["recurringId"].toString()
            )
        }

        val recurringId = createRecurringReservations(request)
        return mapOf("message" to "Recurring reservation created successfully.",
            "recurringId"  to recurringId.toString())
    }

    private fun getNextReservationDate(current: LocalDate, recurringType: RecurringType): LocalDate {
        return when (recurringType) {
            DAILY -> current.plusDays(1)
            WEEKLY -> current.plusWeeks(1)
            EVERY_TWO_WEEKS -> current.plusWeeks(2)
            MONTHLY -> current.plusMonths(1)
        }
    }

    private fun createRecurringReservations(request: ReservationRequest, reservationType: ReservationType = request.type): UUID {
        var current = request.date
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        val recurringId = UUID.randomUUID()

        while (!current.isAfter(request.recurringEndDate!!)) {
            createReservation(request, current, user, recurringId, reservationType)
            current = getNextReservationDate(current, request.recurringType!!)
        }

        return recurringId
    }

    private fun createReservation(
        request: ReservationRequest,
        current: LocalDate,
        user: User,
        recurringId: UUID,
        reservationType: ReservationType = request.type
    ) {
        val reservation = Reservation(
            title = request.title,
            description = request.description,
            date = current,
            startTime = request.startTime,
            endTime = request.endTime,
            classroom = classroomRepository.findOne(request.classroomId)!!,
            type = reservationType,
            numberOfParticipants = request.numberOfParticipants,
            recurringEndDate = request.recurringEndDate,
            recurringType = request.recurringType,
            user = user,
            recurringId = recurringId
        )
        reservationRepository.save(reservation)
    }

    fun createRecurringReservationWithoutCollisions(
        buildingName: String,
        request: ReservationRequest,
        collisions: List<LocalDate>,
        reservationType: ReservationType = request.type
    ): Map<String, Any> {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        val recurringId = UUID.randomUUID()
        var current = request.date

        while (!current.isAfter(request.recurringEndDate!!)) {
            if (!collisions.contains(current)) {
                createReservation(request, current, user, recurringId, reservationType)
            }
            current = getNextReservationDate(current, request.recurringType!!)
        }

        return mapOf(
            "message" to "Recurring reservation created successfully, skipping dates with collisions.",
            "recurringId" to recurringId.toString()
        )
    }

    fun acceptBlockedReservations(
        buildingName: String,
        request: ReservationRequest,
        collisions: List<LocalDate>,
        reservationType: ReservationType = request.type
    ): Map<String, Any> {

        val recurringId = request.recurringId!!
        val allReservations = reservationRepository.findAllByRecurringIdAndBuildingName(recurringId, buildingName)

        allReservations.forEach { reservation ->
            reservation.type = reservationType
            reservationRepository.save(reservation)
        }

        return mapOf(
            "message" to "Recurring reservation created successfully, skipping dates with collisions.",
            "recurringId" to recurringId.toString()
        )
    }

}
