package com.example.demo.client.web;

import com.example.demo.server.mandelbrot.MandelbrotSetResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestClient implements IRestClient {

    private final String HTTP_CLAUSE = "http://";
    private final String API_PATH = "/api/count";

    @Override
    public MandelbrotSetResponseDTO getImage(MandelbrotRequestDTO requestDTO, String url) {

        RestTemplate restTemplate = new RestTemplate();

        MandelbrotSetResponseDTO body
                = restTemplate.postForEntity(HTTP_CLAUSE+url+API_PATH, requestDTO, MandelbrotSetResponseDTO.class).getBody();

        return body;
    }
}
