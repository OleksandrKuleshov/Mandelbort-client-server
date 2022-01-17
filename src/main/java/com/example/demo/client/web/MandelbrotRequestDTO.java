package com.example.demo.client.web;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class MandelbrotRequestDTO {

    private double minCReal;
    private double minCIm;
    private double maxCReal;
    private double maxCIm;

    private int maxNumberOfIterations;


    private int width;
    private int heightFrom;
    private int heightTo;

    private int size;
}
