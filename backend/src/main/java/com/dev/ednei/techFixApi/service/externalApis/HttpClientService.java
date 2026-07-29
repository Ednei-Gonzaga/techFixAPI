package com.dev.ednei.techFixApi.service.externalApis;

import jakarta.mail.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Service
public class HttpClientService {

    public HttpResponse post(String url, String body, ArrayList<Header> headers) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder build = HttpRequest.newBuilder(URI.create(url));

        headers.forEach(header -> build.header(header.getName(), header.getValue()));

        HttpRequest request = build.POST(HttpRequest.BodyPublishers.ofString(body)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse get(String url, ArrayList<Header> headers) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder build = HttpRequest.newBuilder(URI.create(url));

        headers.forEach(header -> build.header(header.getName(), header.getValue()));

        HttpRequest request = build.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse delete(String url, ArrayList<Header> headers) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder build = HttpRequest.newBuilder(URI.create(url));

        headers.forEach(header -> build.header(header.getName(), header.getValue()));

        HttpRequest request = build.DELETE().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}
