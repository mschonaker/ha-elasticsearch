package io.github.mschonaker.haelasticsearch.util.props;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class PropertyProducer {

	private Properties properties = new Properties();

	@Property
	@Produces
	public String produceString(InjectionPoint ip) {
		return properties.getProperty(
				Optional.of(ip.getAnnotated().getAnnotation(Property.class)).map(Property::value).get(),
				Optional.of(ip.getAnnotated().getAnnotation(Property.class)).map(Property::defaultValue).get());
	}

	@Property
	@Produces
	public int produceInt(InjectionPoint ip) {
		return Integer.valueOf(produceString(ip));
	}

	@Property
	@Produces
	public boolean produceBoolean(final InjectionPoint ip) {
		return Boolean.valueOf(produceString(ip));
	}

	@PostConstruct
	public void init() {

		String fileName = System.getProperty("jboss.server.config.dir") + "/application.properties";
		try (FileInputStream fis = new FileInputStream(fileName)) {

			if (fis != null)
				properties.load(fis);

		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
