package agoodfriendalwayspayshisdebts.search.expense.details.search;

import agoodfriendalwayspayshisdebts.search.expense.details.model.ExpensesDetails;
import com.vter.search.PaginatedSearch;

import java.util.UUID;

public class ExpensesDetailsSearch extends PaginatedSearch<ExpensesDetails> {

  public ExpensesDetailsSearch(UUID eventId) {
    this.eventId = eventId;
  }

  public final UUID eventId;
}