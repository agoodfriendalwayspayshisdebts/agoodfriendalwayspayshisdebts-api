package agoodfriendalwayspayshisdebts.web.action.event;

import agoodfriendalwayspayshisdebts.search.event.details.model.EventDetails;
import agoodfriendalwayspayshisdebts.search.event.details.search.EventDetailsSearch;
import com.vter.infrastructure.bus.ExecutionResult;
import com.vter.search.SearchBus;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Resource;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@Resource
public class GetEventDetails {

  @Inject
  public GetEventDetails(SearchBus searchBus) {
    this.searchBus = searchBus;
  }

  @Get("/events/:stringifiedUuid")
  public Optional<EventDetails> retrieve(String stringifiedUuid) {
    final UUID uuid = UUID.fromString(stringifiedUuid);
    final ExecutionResult<EventDetails> result = searchBus.sendAndWaitResponse(new EventDetailsSearch(uuid));
    return Optional.ofNullable(result.data());
  }

  private final SearchBus searchBus;
}