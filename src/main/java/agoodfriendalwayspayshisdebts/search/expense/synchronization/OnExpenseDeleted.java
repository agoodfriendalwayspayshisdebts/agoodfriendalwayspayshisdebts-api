package agoodfriendalwayspayshisdebts.search.expense.synchronization;

import agoodfriendalwayspayshisdebts.model.expense.ExpenseDeletedInternalEvent;
import com.vter.model.internal_event.InternalEventHandler;
import org.jongo.Jongo;

import javax.inject.Inject;

public class OnExpenseDeleted implements InternalEventHandler<ExpenseDeletedInternalEvent> {

  @Inject
  public OnExpenseDeleted(Jongo jongo) {
    this.jongo = jongo;
  }

  @Override
  public void executeInternalEvent(ExpenseDeletedInternalEvent internalEvent) {
    jongo.getCollection("eventexpensesdetails_view")
        .update("{_id:#}", internalEvent.eventId)
        .with("{$inc:{expenseCount:-1},$pull:{expenses:{id:#}}}", internalEvent.expense.id());
  }

  private final Jongo jongo;
}