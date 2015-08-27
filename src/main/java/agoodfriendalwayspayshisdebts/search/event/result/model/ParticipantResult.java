package agoodfriendalwayspayshisdebts.search.event.result.model;

import agoodfriendalwayspayshisdebts.model.participant.Participant;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class ParticipantResult {
  private static double INITIAL_DEBT = 0D;

  private String participantName;
  private double totalSpent;
  private double totalDebt;
  private Map<UUID, DebtTowardsParticipant> debtsDetail = Maps.newHashMap();

  @SuppressWarnings("unused")
  private ParticipantResult() {}

  private ParticipantResult(String participantName) {
    this.participantName = participantName;
  }

  public static ParticipantResult forParticipant(Participant participant, Map<UUID, String> participantsNames) {
    final ParticipantResult participantResult = new ParticipantResult(participant.name());
    participantsNames.entrySet().stream()
        .filter(participantEntry -> !participantEntry.getKey().equals(participant.id()))
        .forEach(participantEntry -> participantResult.debtsDetail
            .put(participantEntry.getKey(), new DebtTowardsParticipant(participantEntry.getValue(), INITIAL_DEBT)));
    return participantResult;
  }

  public String participantName() {
    return participantName;
  }

  public double totalSpent() {
    return totalSpent;
  }

  public double totalDebt() {
    return totalDebt;
  }

  public void increaseTotalAmountSpentBy(double amount) {
    totalSpent += amount;
  }

  public void updateDebtTowards(UUID creditorId, double amount) {
    double currentDebtTowardsCreditor = debtsDetail.get(creditorId).amount;
    totalDebt = totalDebt - currentDebtTowardsCreditor + amount;
    debtsDetail.get(creditorId).amount = amount;
  }

  public double debtTowards(UUID participantId) {
    return debtsDetail.get(participantId).amount;
  }
}
