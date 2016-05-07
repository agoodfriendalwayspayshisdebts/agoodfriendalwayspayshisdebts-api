package agoodfriendalwayspayshisdebts.search.expense.synchronization

import agoodfriendalwayspayshisdebts.infrastructure.persistence.memory.WithMemoryRepository
import agoodfriendalwayspayshisdebts.model.RepositoryLocator
import agoodfriendalwayspayshisdebts.model.event.Event
import agoodfriendalwayspayshisdebts.model.expense.Expense
import agoodfriendalwayspayshisdebts.model.expense.ExpenseAddedInternalEvent
import agoodfriendalwayspayshisdebts.model.participant.Participant
import agoodfriendalwayspayshisdebts.search.expense.model.ExpensesDetails
import com.vter.search.WithJongo
import org.junit.Rule
import spock.lang.Specification

class OnExpenseAddedTest extends Specification {
  @Rule
  WithJongo jongo = new WithJongo()

  @Rule
  WithMemoryRepository repository = new WithMemoryRepository()

  Participant kim = new Participant("kim", 1, null)
  Event event = new Event("event", "€", [kim])

  OnExpenseAdded handler

  def setup() {
    handler = new OnExpenseAdded(jongo.jongo())
    RepositoryLocator.events().add(event)
  }

  def "can update the expenses details of the event"() {
    given:
    jongo.collection("expensesdetails_view") << [
        _id: event.id,
        totalCount: 1,
        items: [[label: "label", purchaserName: kim.name(), amount: 2, participantsNames: [kim.name()], description: "hello"]]
    ]

    when:
    def expense = new Expense("hey", kim.id, 4, [kim.id], event.id)
    handler.executeInternalEvent(new ExpenseAddedInternalEvent(expense))

    then:
    // TODO: For some reason (and since jongo 1.2) when checking with GMongo the list is transformed into a map
    // TODO: (i.e. [0:[...], 1:[...]]) after jongo update in test environment. Therefore the deserialized object is used instead.
    def details = jongo.jongo().getCollection("expensesdetails_view").findOne().as(ExpensesDetails.class)
    details.eventId == event.id
    details.totalCount == 2
    details.items.size() == 2
    details.items[1].id == expense.id
    details.items[1].label == "hey"
    details.items[1].purchaserName == "kim"
    details.items[1].amount == 4
    details.items[1].participantsNames == ["kim"]
    details.items[1].description == null
    details.items[1].eventId == event.id
  }

  def "creates the expenses details if it does not exist yet"() {
    when:
    def expense = new Expense("hey", kim.id, 4, [kim.id], event.id)
    handler.executeInternalEvent(new ExpenseAddedInternalEvent(expense))

    then:
    def document = jongo.collection("expensesdetails_view").findOne()
    document["_id"] == event.id
    document["items"].size() == 1
  }
}