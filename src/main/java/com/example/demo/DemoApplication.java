package com.example.demo;

import com.example.demo.client.Client;
import com.example.demo.server.Server;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Collections;

public class DemoApplication {

	public static void main(String[] args) {

		if (args[0].equalsIgnoreCase("client")) {

			new SpringApplicationBuilder()
					.sources(Client.class)
					.web(WebApplicationType.NONE)
					.run(args);

		} else if (args[0].equalsIgnoreCase("server")) {

			new SpringApplicationBuilder()
					.sources(Server.class)
					.properties(Collections.singletonMap("server.port", args[1]))
					.run(args);
		} else {

			throw new IllegalArgumentException("Not implemented");
		}
	}
}
