package com.learncamel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jasypt.JasyptPropertiesParser;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.learncamel.route.SimpleRouteBuilder;

@SpringBootApplication
public class LearncamelSpringBootApplication {

	@Autowired
	static CamelContext camelContext;
	
	@Autowired
	static 	SimpleRouteBuilder simpleRouteBuilder;
	
	@Autowired
	static 	PropertiesComponent pc;
	public static void main(String[] args) throws Exception {
		SpringApplication.run(LearncamelSpringBootApplication.class, args);
		
		JasyptPropertiesParser jasypt = new JasyptPropertiesParser();
		// and set the master password
		jasypt.setPassword("secret");

		// create the properties component
		pc.setLocation("file:D:\\ericsson\\workspace\\osm-camel-ws1\\camel-spring-boot-jasypt\\src\\main\\resources\\application.propertiess");
		// and use the jasypt properties parser so we can decrypt values
		pc.setPropertiesParser(jasypt);
		
		    camelContext.addComponent("properties", pc);
		    camelContext.addRoutes(simpleRouteBuilder);
		    camelContext.start();
		    
		    try {
		        ProducerTemplate producer = camelContext.createProducerTemplate();
		        String result = producer.requestBody("direct:start", "John", String.class);
		        System.out.println("result: " + result);
		        Assert.assertEquals("Hi John the decrypted password is: admin", result.trim());
		    } finally {
		    	//camelContext.close();
		    }
		    
		    
	}
	
}
