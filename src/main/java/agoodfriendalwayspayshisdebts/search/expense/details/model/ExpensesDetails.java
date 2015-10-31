package agoodfriendalwayspayshisdebts.search.expense.details.model;

import com.google.common.collect.Lists;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.List;
import java.util.UUID;

public class ExpensesDetails {
  @MongoId
  public UUID eventId;
  public int expenseCount;
  public List<ExpenseDetails> expenses = Lists.newArrayList();

  @SuppressWarnings("unused")
  private ExpensesDetails() {}
}