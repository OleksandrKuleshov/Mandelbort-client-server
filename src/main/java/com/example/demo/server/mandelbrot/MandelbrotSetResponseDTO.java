package com.example.demo.server.mandelbrot;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MandelbrotSetResponseDTO {

    private int[][] image;
}
