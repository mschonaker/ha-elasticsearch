package io.github.mschonaker.haelasticsearch.api;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

@Provider
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@Path("messages")
public class MessagesResource {

	@Inject
	Client client;

	@GET
	public Results<Message> find(@QueryParam("q") @DefaultValue("*:*") String query) {

		SearchResponse response = client.prepareSearch("messages").setTypes("message")//
				.setQuery(QueryBuilders.termQuery(null, query)).get();

		SearchHits hits = response.getHits();

		long totalHits = hits.getTotalHits();
		Stream<SearchHit> stream = StreamSupport.stream(hits.spliterator(), true);

		return new Results<Message>(totalHits, stream.map(MessagesResource::toMessage).collect(Collectors.toList()));

	}

	private static Message toMessage(SearchHit doc) {
		return new Message(//
				doc.getId(), //
				(Date) doc.field("date").getValue(), //
				(String) doc.field("message").getValue());
	}

}
