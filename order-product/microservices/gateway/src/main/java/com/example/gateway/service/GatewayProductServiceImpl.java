package com.example.gateway.service;

import com.example.gateway.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@CrossOrigin
@RestController
@Configuration
public class GatewayProductServiceImpl implements GatewayProductService {

    private final WebClient webClient;

    @Autowired
    public GatewayProductServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private static final String productServiceUrl = "http://product-service";

    @Override
    public Mono<Response> addProduct(ProductRequest request) {

        Status status = new Status();
        status.setStatusCode("9999");
        status.setStatusDesc("Internal Error");

        Response response = new Response();
        response.setStatus(status);

        String Url = productServiceUrl + "/addProduct";

        return webClient
                .post()
                .uri(Url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request, ProductRequest.class)
                .retrieve()
                .bodyToMono(Response.class)
                .defaultIfEmpty(response)
                .flatMap(response1 -> {

                    status.setStatusCode(response1.getStatus().getStatusCode());
                    status.setStatusDesc(response1.getStatus().getStatusDesc());
                    response.setStatus(status);
                    response.setContent(response1.getContent());

                    return Mono.just(response);
                });

    }

    @Override
    public Mono<Response> updateProduct(ProductRequest request) {
        Status status = new Status();
        status.setStatusCode("9999");
        status.setStatusDesc("Internal Error");

        Response response = new Response();
        response.setStatus(status);

        String Url = productServiceUrl + "/updateProduct";

        return webClient
                .post()
                .uri(Url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request, ProductRequest.class)
                .retrieve()
                .bodyToMono(Response.class)
                .defaultIfEmpty(response)
                .flatMap(response1 -> {

                    status.setStatusCode(response1.getStatus().getStatusCode());
                    status.setStatusDesc(response1.getStatus().getStatusDesc());
                    response.setStatus(status);
                    response.setContent(response1.getContent());

                    return Mono.just(response);
                });
    }

    @Override
    public Mono<Response> deleteProduct(ProductRequest request) {
        Status status = new Status();
        status.setStatusCode("9999");
        status.setStatusDesc("Internal Error");

        Response response = new Response();
        response.setStatus(status);

        String Url = productServiceUrl + "/deleteProduct";

        return webClient
                .post()
                .uri(Url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request, ProductRequest.class)
                .retrieve()
                .bodyToMono(Response.class)
                .defaultIfEmpty(response)
                .flatMap(response1 -> {

                    status.setStatusCode(response1.getStatus().getStatusCode());
                    status.setStatusDesc(response1.getStatus().getStatusDesc());
                    response.setStatus(status);
                    response.setContent(response1.getContent());

                    return Mono.just(response);
                });
    }

    @Override
    public Mono<Response> viewProductAdmin(ProductRequest request) {
        Status status = new Status();
        status.setStatusCode("9999");
        status.setStatusDesc("Internal Error");

        Response response = new Response();
        response.setStatus(status);

        String Url = productServiceUrl + "/viewProduct";

        return webClient
                .post()
                .uri(Url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request, ProductRequest.class)
                .retrieve()
                .bodyToMono(Response.class)
                .defaultIfEmpty(response)
                .flatMap(response1 -> {

                    status.setStatusCode(response1.getStatus().getStatusCode());
                    status.setStatusDesc(response1.getStatus().getStatusDesc());
                    response.setStatus(status);
                    response.setContent(response1.getContent());

                    return Mono.just(response);
                });
    }

    @Override
    public Mono<Response> viewCategories(CategoryRequest request) {
        Status status = new Status();
        status.setStatusCode("9999");
        status.setStatusDesc("Internal Error");

        Response response = new Response();
        response.setStatus(status);

        String Url = productServiceUrl + "/viewCategories";

        return webClient
                .post()
                .uri(Url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request, CategoryRequest.class)
                .retrieve()
                .bodyToMono(Response.class)
                .defaultIfEmpty(response)
                .flatMap(response1 -> {

                    status.setStatusCode(response1.getStatus().getStatusCode());
                    status.setStatusDesc(response1.getStatus().getStatusDesc());
                    response.setStatus(status);
                    response.setContent(response1.getContent());

                    return Mono.just(response);
                });
    }
}
