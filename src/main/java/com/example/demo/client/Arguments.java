package com.example.demo.client;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ToString
public class Arguments {

    private double minCReal;
    private double minCIm;
    private double maxCReal;
    private double maxCIm;

    private int maxNumberOfItr;

    private int width;
    private int height;

    private int numberOfRequiredDivisions;

    private List<String> servers;

}
