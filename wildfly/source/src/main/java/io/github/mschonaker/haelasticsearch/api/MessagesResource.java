package io.github.mschonaker.haelasticsearch.api;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("messages")
public class MessagesResource {

	@Inject
	Client client;

	@GET
	public Results<Message> findAll() {

		SearchHits hits = client.prepareSearch("messages").setTypes("message")//
				.setFetchSource(true).setQuery(QueryBuilders.matchAllQuery())//
				.get().getHits();

		Stream<SearchHit> stream = StreamSupport.stream(hits.spliterator(), true);

		return new Results<Message>(hits.getTotalHits(),
				stream.map(MessagesResource::toMessage).collect(Collectors.toList()));
	}

	private static Message toMessage(SearchHit doc) {
		Message message = Mapper.from(doc.getSource());
		message.setId(doc.getId());
		return message;
	}
}
