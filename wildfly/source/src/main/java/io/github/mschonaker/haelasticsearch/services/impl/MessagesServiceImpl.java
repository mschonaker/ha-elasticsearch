package io.github.mschonaker.haelasticsearch.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import io.github.mschonaker.haelasticsearch.services.Message;
import io.github.mschonaker.haelasticsearch.services.MessagesService;
import io.github.mschonaker.haelasticsearch.services.Results;

public class MessagesServiceImpl implements MessagesService {

	private final Client client;

	public MessagesServiceImpl(Client client) {
		this.client = client;
	}

	@Override
	public Message find(String id) {
		GetResponse response = client.prepareGet("messages", "message", id)//
				.setFetchSource(true)//
				.get();

		Message message = Mapper.from(response.getSourceAsMap());
		message.setId(response.getId());
		return message;
	}

	@Override
	public void update(Message message) {

		client.prepareIndex("messages", "message", message.getId())//
				.setSource(Mapper.toSource(message.getMessage()))//
				.setRefreshPolicy(RefreshPolicy.WAIT_UNTIL)//
				.execute();

	}

	@Override
	public void insert(String message) {
		client.prepareIndex("messages", "message")//
				.setSource(Mapper.toSource(message))//
				.setRefreshPolicy(RefreshPolicy.WAIT_UNTIL)//
				.get();
	}

	@Override
	public void delete(String id) {

		client.prepareDelete("messages", "message", id)//
				.setRefreshPolicy(RefreshPolicy.WAIT_UNTIL)//
				.get();

	}

	@Override
	public Results<Message> findAll() {
		long total = 0;
		List<Message> items = new ArrayList<>();

		try {

			SearchHits hits = client.prepareSearch("messages").setTypes("message")//
					.setFetchSource(true)//
					.setQuery(QueryBuilders.matchAllQuery())//
					.addSort("date", SortOrder.DESC)//
					.get().getHits();

			total = hits.getTotalHits();
			StreamSupport.stream(hits.spliterator(), true)//
					.map(this::toMessage)//
					.forEach(items::add);

		} catch (Exception e) {
		}

		return new Results<Message>(total, items);

	}

	private Message toMessage(SearchHit doc) {
		Message message = Mapper.from(doc.getSource());
		message.setId(doc.getId());
		return message;
	}

}
