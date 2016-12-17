package io.github.mschonaker.haelasticsearch.rest;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;

@Provider
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@Path("status")
public class StatusResource {

	@Inject
	Client client;

	@GET
	@Path("hostname")
	public String getHostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "<Unknown>";
		}
	}

	@GET
	@Path("cluster-info")
	public ClusterHealthResponse getElasticSearchClusterInfo() {
		return client.admin().cluster().prepareHealth().get();
	}
}
