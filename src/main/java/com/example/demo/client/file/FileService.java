package com.example.demo.client.file;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@Service
public class FileService implements IFileService{

    @Override
    public void saveImageToPgm(int[][] image) {

        int width = image.length;
        int height = image[0].length;
        try {
            PrintStream output = new PrintStream(new FileOutputStream("image.pgm"));

            output.println("P2");
            output.println(width + " " + height);
            output.println(255);

            for (int[] ints : image) {

                for (int row = 0; row < height; row++) {
                    output.println(ints[row]);
                }
            }

            output.close();
        } catch (IOException e) {
            throw new FileException("Could not save image, e: " + e.getMessage(), e.getCause());
        }
    }
}
