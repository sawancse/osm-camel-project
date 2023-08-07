package com.learncamel.route;

import java.io.InputStream;
import java.security.KeyStore.Entry.Attribute;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecResult;
import org.springframework.stereotype.Component;

@Component
public class SimpleCamelRoute extends RouteBuilder {
	@Override
	public void configure() throws Exception {

		from("timer:hello?period=30s")
		.to("direct:callCamelExecutor");

		 from("direct:callCamelExecutor")
		 .to("exec:java?args=-server -version&useStderrOnEmptyStdout=true&workingDir=D:\\\\ericsson\\\\workspace\\\\osm-camel-ws1\\\\camel-spring-boot-exec-lab1")
		 .log("o/p: ${body}");
	}
}
