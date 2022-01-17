package com.example.demo.server.mandelbrot;

import com.example.demo.client.web.MandelbrotRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    MandelbrotSetService mandelbrotSetService;

    @GetMapping("/set")
    public ResponseEntity<String> set() {

        log.info("Received GET request for /set");

        return ResponseEntity.ok("set");
    }

    @GetMapping("/check")
    public ResponseEntity<String> check() {

        log.info("Received GET request for /check");

        return ResponseEntity.ok("check");
    }

    @PostMapping("/count")
    public ResponseEntity<MandelbrotSetResponseDTO> count (@RequestBody MandelbrotRequestDTO mandelbrotRequestDTO) {

        log.info("Received POST with body={}", mandelbrotRequestDTO);

        return ResponseEntity.ok(mandelbrotSetService.processRequest(mandelbrotRequestDTO));
    }
}
