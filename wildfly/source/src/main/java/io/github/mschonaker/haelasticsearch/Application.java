package io.github.mschonaker.haelasticsearch;

import java.net.InetSocketAddress;
import java.util.Arrays;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import io.github.mschonaker.haelasticsearch.services.MessagesService;
import io.github.mschonaker.haelasticsearch.services.impl.MessagesServiceImpl;
import io.github.mschonaker.haelasticsearch.util.props.Property;

@ApplicationPath("/api")
public class Application extends javax.ws.rs.core.Application {

	private Client client;

	@Inject
	@Property(value = "elasticsearch.addresses", defaultValue = "localhost:10001,localhost:10002,localhost:10003")
	private String elasticsearchAddresses;

	@Produces
	@ApplicationScoped
	public Client createClient() {

		assert elasticsearchAddresses != null;

		PreBuiltTransportClient client = new PreBuiltTransportClient(Settings.EMPTY);

		Arrays.stream(elasticsearchAddresses.split("\\,"))
				.map(s -> new InetSocketAddress(s.substring(0, s.lastIndexOf(":")),
						Integer.parseInt(s.substring(s.lastIndexOf(":") + 1, s.length()))))
				.map(InetSocketTransportAddress::new)//
				.forEach(client::addTransportAddress);

		return client;
	}

	@Produces
	@ApplicationScoped
	public MessagesService createMessagesService() {
		return new MessagesServiceImpl(createClient());
	}

	@PreDestroy
	public void destroy() {
		if (client != null)
			client.close();
	}
}
