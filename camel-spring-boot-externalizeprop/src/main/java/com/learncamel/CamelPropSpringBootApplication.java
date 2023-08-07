package com.learncamel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jasypt.JasyptPropertiesParser;
import org.apache.camel.component.properties.PropertiesComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.learncamel.route.PropertiesReaderRoute;
import com.learncamel.route.SimpleRouteBuilder;

@SpringBootApplication
public class CamelPropSpringBootApplication {

	@Autowired
	static CamelContext camelContext;

	@Autowired
	static SimpleRouteBuilder simpleRouteBuilder;

	@Autowired
	static PropertiesReaderRoute propertiesReaderRoute;

	@Autowired
	static PropertiesComponent pc1;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CamelPropSpringBootApplication.class, args);

		JasyptPropertiesParser jasypt = new JasyptPropertiesParser();
		// and set the master password
		jasypt.setPassword("secret");
		pc1 = new PropertiesComponent();
		// create the properties component
		// pc.setLocation("file:D:\\ericsson\\workspace\\osm-camel-ws1\\camel-spring-boot-jasypt\\src\\main\\resources\\application.propertiess");
		pc1.setLocation("com/learncamel/application.properties,application.properties");
		// pc.setLocation("application.properties");
		// and use the jasypt properties parser so we can decrypt values
		pc1.setPropertiesParser(jasypt);
		camelContext.addComponent("properties", pc1);

		camelContext.addRoutes(simpleRouteBuilder);
		// camelContext.addRoutes(propertiesReaderRoute);
		camelContext.start();

		try {
			ProducerTemplate producer = camelContext.createProducerTemplate();
			String result = producer.requestBody("direct:start", "John", String.class);
			System.out.println("result: " + result);
			// Assert.assertEquals("Hi John the decrypted password is: admin",
			// result.trim());
		} finally {
			// camelContext.close();
		}

		// pc.setLocation("com/fusesource/cheese.properties,com/fusesource/bar.properties");

	}

}
