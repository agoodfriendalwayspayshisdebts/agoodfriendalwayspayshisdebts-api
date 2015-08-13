package agoodfriendalwayspayshisdebts.search.event.details.model;

import agoodfriendalwayspayshisdebts.model.participant.Participant;

import java.util.UUID;

public class ParticipantDetails {
  public UUID id;
  public String name;
  public int share;
  public String email;

  public ParticipantDetails() {}

  public static ParticipantDetails fromParticipant(Participant participant) {
    ParticipantDetails participantDetails = new ParticipantDetails();
    participantDetails.id = participant.id();
    participantDetails.name = participant.name();
    participantDetails.share = participant.share();
    participantDetails.email = participant.email();
    return participantDetails;
  }
}
