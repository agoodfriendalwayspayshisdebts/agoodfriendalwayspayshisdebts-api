package agoodfriendalwayspayshisdebts.infrastructure.persistence.mongo;

import agoodfriendalwayspayshisdebts.model.RepositoryLocator;
import agoodfriendalwayspayshisdebts.model.event.EventRepository;
import com.vter.infrastructure.persistence.mongo.MongoLinkContext;

import javax.inject.Inject;

public class MongoRepositoryLocator extends RepositoryLocator {

  @Inject
  public MongoRepositoryLocator(MongoLinkContext mongoLinkContext) {
    this.mongoLinkContext = mongoLinkContext;
  }

  @Override
  protected EventRepository getEvents() {
    return new MongoEventRepository(mongoLinkContext.currentSession());
  }

  private final MongoLinkContext mongoLinkContext;
}
