package io.github.mschonaker.haelasticsearch.restjs;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class JSAPIContextListener implements ServletContextListener {

	private static final String SERVLET_NAME = "org.jboss.resteasy.jsapi.JSAPIServlet";

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		try {

			Class<? extends Servlet> type = (Class<? extends Servlet>) Class.forName(SERVLET_NAME);
			context.addServlet(SERVLET_NAME, type).addMapping("/rest.js");

		} catch (Exception e) {
			context.log("Unable to initialize " + SERVLET_NAME, e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
