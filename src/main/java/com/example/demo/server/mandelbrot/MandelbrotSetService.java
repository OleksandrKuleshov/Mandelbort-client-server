package com.example.demo.server.mandelbrot;

import com.example.demo.client.web.MandelbrotRequestDTO;
import org.apache.commons.math3.complex.Complex;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class MandelbrotSetService {

    public MandelbrotSetResponseDTO processRequest(MandelbrotRequestDTO requestDTO) {

        return new MandelbrotSetResponseDTO()
                .setImage(getImage(requestDTO));
    }

    private int[][] getImage(MandelbrotRequestDTO requestDTO) {

        int size = requestDTO.getSize();

        double maxCReal = requestDTO.getMaxCReal();
        double maxCIm = requestDTO.getMaxCIm();
        double minCReal = requestDTO.getMinCReal();
        double minCIm = requestDTO.getMinCIm();
        int n = requestDTO.getMaxNumberOfIterations();

        int[][] image = new int[size][size];

        for (int row = 0; row < requestDTO.getWidth(); row++) {
            for (int col = requestDTO.getHeightFrom(); col < requestDTO.getHeightTo(); col ++) {

                double x = normalizeX(minCReal,maxCReal, size, col);
                double y = normalizeY(minCIm, maxCIm, size, row);
                Complex z0 = new Complex(x, y);
                int colorValue = countNumberOfIterations(z0, n) % 256;
                Color color = new Color(colorValue, colorValue, colorValue);
                image[row][col] = color.getRGB();
            }
        }

        return image;
    }


    private int countNumberOfIterations(Complex z0, int max) {

        Complex z = z0;

        for (int i = 0; i<max; i++) {
            if (z.abs() > 2.0) {
                return i;
            }
            z = getZ(z, z0);
        }
        return max;
    }

    private Complex getZ(Complex z, Complex c) {

        return z.pow(2).add(c);
    }

    private double normalizeX(double minCReal, double maxCReal, int height, double x) {

        return minCReal + (x - 0) * (maxCReal - minCReal) / (height - 0);
    }

    private double normalizeY(double minCIm, double maxCIm, int width, double y) {

        return minCIm + (y - 0) * (maxCIm - minCIm) / (width - 0);
    }
}
