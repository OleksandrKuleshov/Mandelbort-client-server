package com.example.demo.client.service;

import com.example.demo.client.Arguments;
import com.example.demo.client.file.IFileService;
import com.example.demo.client.web.IRestClient;
import com.example.demo.client.web.LoadBalancer;
import com.example.demo.client.web.MandelbrotRequestDTO;
import com.example.demo.server.mandelbrot.MandelbrotSetResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

    @Autowired
    private final IFileService fileService;
    @Autowired
    private final IRestClient restClient;



    @Override
    public void process(String... args) {

        Arguments arguments = buildArguments(args);

        Queue<MandelbrotRequestDTO> requestDTOQueue = prepareRequestDTOs(arguments);

        int[][] result = new int[arguments.getHeight()][arguments.getWidth()];

        System.out.println("Calling servers to process image.");

        ExecutorService executorService = Executors.newFixedThreadPool(requestDTOQueue.size());

        List<CompletableFuture<Void>> futureRequests = buildRequests(requestDTOQueue, arguments.getServers(), result, executorService);

        CompletableFuture.allOf(futureRequests.toArray(new CompletableFuture[0])).join();

        executorService.shutdownNow();

        System.out.println("Waiting for servers to return a response...");

        System.out.println("Saving file to disc...");

        fileService.saveImageToPgm(result);

        System.out.println("File has successfully been saved to project folder, with name image.pgm");
        System.out.println("Exiting");
    }

    private Arguments buildArguments(String... args) {

        return new Arguments()
                .setMinCReal(Integer.parseInt(args[1]))
                .setMinCIm(Integer.parseInt(args[2]))
                .setMaxCReal(Integer.parseInt(args[3]))
                .setMaxCIm(Integer.parseInt(args[4]))
                .setMaxNumberOfItr(Integer.parseInt(args[5]))
                .setWidth(Integer.parseInt(args[6]))
                .setHeight(Integer.parseInt(args[7]))
                .setNumberOfRequiredDivisions(Integer.parseInt(args[8]))
                .setServers(getServers(args));
    }

    private List<CompletableFuture<Void>> buildRequests(Queue<MandelbrotRequestDTO> requestDTOQueue, List<String> urls,
                                                        int[][] result, ExecutorService executorService) {

        List<CompletableFuture<Void>> list = new ArrayList<>();

        LoadBalancer loadBalancer = new LoadBalancer(urls);

        while(!requestDTOQueue.isEmpty()) {

            MandelbrotRequestDTO request = requestDTOQueue.poll();

            CompletableFuture<Void> voidCompletableFuture
                    = CompletableFuture.supplyAsync(() -> callServer(request, loadBalancer.getUrl()), executorService)
                    .thenAccept(response -> copyResponseIntoResult(result,
                            response.getImage(),
                            request.getWidth(),
                            request.getHeightFrom(),
                            request.getHeightTo()));
            list.add(voidCompletableFuture);
        }

        return list;
    }

    private Queue<MandelbrotRequestDTO> prepareRequestDTOs(Arguments arguments) {

        Queue<MandelbrotRequestDTO> requestDTOQueue = new LinkedList<>();

        int numberOfRequiredDivisions = arguments.getNumberOfRequiredDivisions();
        int sizeOfOneStep = arguments.getHeight() / numberOfRequiredDivisions;

        int current = 0;
        for (int i=0; i<numberOfRequiredDivisions; i++) {

            MandelbrotRequestDTO mandelbrotRequestDTO = buildRequestDTO(arguments);

            mandelbrotRequestDTO.setHeightFrom(current);
            mandelbrotRequestDTO.setHeightTo(current + sizeOfOneStep);
            mandelbrotRequestDTO.setWidth(arguments.getWidth());
            mandelbrotRequestDTO.setSize(arguments.getHeight());
            requestDTOQueue.add(mandelbrotRequestDTO);

            current += sizeOfOneStep;
        }

        return requestDTOQueue;
    }

    private void copyResponseIntoResult(int[][] result, int[][] responseImage, int width, int heightFrom, int heightTo) {

        for (int i =0; i<width; i++) {
            if (heightTo - heightFrom >= 0)
                System.arraycopy(responseImage[i], heightFrom, result[i], heightFrom, heightTo - heightFrom);
        }
    }

    private MandelbrotSetResponseDTO callServer (MandelbrotRequestDTO requestDTO, String url) {

        System.out.println("Calling server with url: " + url);

        return restClient.getImage(requestDTO, url);
    }

    private MandelbrotRequestDTO buildRequestDTO(Arguments arguments) {

        return new MandelbrotRequestDTO()
                .setMinCReal(arguments.getMinCReal())
                .setMinCIm(arguments.getMinCIm())
                .setMaxCReal(arguments.getMaxCReal())
                .setMaxCIm(arguments.getMaxCIm())
                .setMaxNumberOfIterations(arguments.getMaxNumberOfItr());
    }

    private List<String> getServers(String... args) {

       return Arrays.asList(args).subList(9, args.length);
    }
}
