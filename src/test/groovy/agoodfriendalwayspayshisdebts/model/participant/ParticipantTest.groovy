package agoodfriendalwayspayshisdebts.model.participant

import agoodfriendalwayspayshisdebts.infrastructure.persistence.memory.WithMemoryRepository
import agoodfriendalwayspayshisdebts.model.activity.OperationPerformedInternalEvent
import agoodfriendalwayspayshisdebts.model.activity.OperationType
import com.vter.model.internal_event.WithEventBus
import org.junit.Rule
import spock.lang.Specification

class ParticipantTest extends Specification {
  @Rule
  WithEventBus eventBus = new WithEventBus()

  @Rule
  WithMemoryRepository memoryRepository = new WithMemoryRepository()

  def "can create a participant with a name and a share"() {
    given:
    def participant = new Participant("kim", 1, "kim@email.com")

    expect:
    participant.id != null
    participant.name() == "kim"
    participant.share() == 1
    participant.email() == "kim@email.com"
  }

  def "two participants with the same id are equal"() {
    given:
    def kim = new Participant("kim", 1, null)
    def ben = new Participant("ben", 1, null)
    ben.id = kim.id

    expect:
    kim == ben
  }

  def "two participants with the same name are not necessarily equal"() {
    given:
    def kim = new Participant("kim", 1, null)
    def kim2 = new Participant("kim", 1, null)

    expect:
    kim != kim2
  }

  def "updates a participant"() {
    given:
    def eventId = UUID.randomUUID()
    def participant = new Participant("", 1, "")
    participant.eventId(eventId)

    when:
    participant.update("a@email.com")

    then:
    participant.email() == "a@email.com"
  }

  def "emits an event to notify of the participant edited operation"() {
    given:
    def eventId = UUID.randomUUID()
    def participant = new Participant("lea", 1, null)
    participant.eventId(eventId)

    when:
    participant.update("email")

    then:
    def internalEvent = eventBus.bus.lastEvent(OperationPerformedInternalEvent)
    internalEvent.eventId == eventId
    internalEvent.creationDate != null
    internalEvent.operationType == OperationType.PARTICIPANT_EDITED
    internalEvent.operationData == "lea"
  }

  def "emits an event when a participant is updated"() {
    given:
    def eventId = UUID.randomUUID()
    def participant = new Participant("", 1, "")
    participant.eventId(eventId)

    when:
    participant.update("a@email.com")

    then:
    def internalEvent = eventBus.bus.lastEvent(ParticipantUpdatedInternalEvent)
    internalEvent != null
    internalEvent.eventId == eventId
    internalEvent.participantId == participant.id
  }
}
