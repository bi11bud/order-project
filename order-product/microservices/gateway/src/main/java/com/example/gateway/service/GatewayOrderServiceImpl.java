package com.example.gateway.service;

import com.example.gateway.dto.OrderRequest;
import com.example.gateway.dto.Request;
import com.example.gateway.dto.Response;
import com.example.gateway.dto.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@Configuration
public class GatewayOrderServiceImpl implements GatewayOrderService{

    private final WebClient webClient;

    @Autowired
    public GatewayOrderServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private static final String orderServiceUrl = "http://order-service";

    @Override
    public Mono<Response> orderProduct(OrderRequest request) {

        Status status = new Status();
        status.setStatusCode("9999");
        status.setStatusDesc("Internal Error");

        Response response = new Response();
        response.setStatus(status);

        String Url = orderServiceUrl + "/orderProduct";

        return webClient
                .post()
                .uri(Url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request, OrderRequest.class)
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
    public Mono<Response> viewOrder(OrderRequest request) {
        Status status = new Status();
        status.setStatusCode("9999");
        status.setStatusDesc("Internal Error");

        Response response = new Response();
        response.setStatus(status);

        String Url = orderServiceUrl + "/viewOrder";

        return webClient
                .post()
                .uri(Url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request, OrderRequest.class)
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
    public Mono<Response> viewProduct(OrderRequest request) {
        Status status = new Status();
        status.setStatusCode("9999");
        status.setStatusDesc("Internal Error");

        Response response = new Response();
        response.setStatus(status);

        String Url = orderServiceUrl + "/viewProduct";

        return webClient
                .post()
                .uri(Url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(request, OrderRequest.class)
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
