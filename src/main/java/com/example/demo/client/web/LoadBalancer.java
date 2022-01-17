package com.example.demo.client.web;

import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
public class LoadBalancer {

    private int count;

    private final List<String> urls;

    public LoadBalancer(List<String> urls) {

        count = 0;
        this.urls = urls;
    }

    public String getUrl() {

        if (count == urls.size()) {
            count = 0;
        }

        return urls.get(count++);
    }
}
