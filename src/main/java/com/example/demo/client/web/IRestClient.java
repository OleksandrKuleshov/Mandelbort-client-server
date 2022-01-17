package com.example.demo.client.web;

import com.example.demo.server.mandelbrot.MandelbrotSetResponseDTO;

public interface IRestClient {

    MandelbrotSetResponseDTO getImage(MandelbrotRequestDTO requestDTO, String address);
}
