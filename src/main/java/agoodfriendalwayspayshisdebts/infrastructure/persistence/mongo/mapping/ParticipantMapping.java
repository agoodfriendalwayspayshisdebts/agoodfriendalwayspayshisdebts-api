package agoodfriendalwayspayshisdebts.infrastructure.persistence.mongo.mapping;

import agoodfriendalwayspayshisdebts.model.participant.Participant;
import org.mongolink.domain.mapper.AggregateMap;

/* This is implicitly used by MongoLink */
@SuppressWarnings("unused")
public class ParticipantMapping extends AggregateMap<Participant> {

  @Override
  public void map() {
    property().onField("id");
    property().onField("name");
    property().onField("share");
    property().onField("email");
    property().onField("eventId");
  }
}
