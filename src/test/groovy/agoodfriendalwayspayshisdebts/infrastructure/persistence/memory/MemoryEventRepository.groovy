package agoodfriendalwayspayshisdebts.infrastructure.persistence.memory

import agoodfriendalwayspayshisdebts.model.event.Event
import agoodfriendalwayspayshisdebts.model.event.EventRepository
import com.vter.infrastructure.persistence.memory.MemoryRepositoryWithUuid

class MemoryEventRepository extends MemoryRepositoryWithUuid<Event> implements EventRepository {
}
