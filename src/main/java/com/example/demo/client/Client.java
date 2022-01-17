package com.example.demo.client;

import com.example.demo.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class Client implements CommandLineRunner {

    @Autowired
    private ClientService clientService;

    @Override
    public void run(String... args) {

        log.info("Client application started, with args={}", Arrays.toString(args));

        clientService.process(args);
    }

}
