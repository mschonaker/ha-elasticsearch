package io.github.mschonaker.haelasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.ApplicationPath;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

@ApplicationPath("/api")
public class Application extends javax.ws.rs.core.Application {

	private Client client;

	@SuppressWarnings("resource")
	@Produces
	@ApplicationScoped
	public Client createClient() throws UnknownHostException {
		return client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch-a"), 9300))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch-b"), 9300))
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch-c"), 9300));
	}

	@PreDestroy
	public void destroy() {
		if (client != null)
			client.close();
	}
}
