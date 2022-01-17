package com.example.demo.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Server {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printWelcomeMessage() {
        System.out.println("Server is up and ready to take requests, port:" + env.getProperty("server.port"));
    }
}
