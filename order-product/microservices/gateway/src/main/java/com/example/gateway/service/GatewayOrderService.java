package com.example.gateway.service;

import com.example.gateway.dto.OrderRequest;
import com.example.gateway.dto.Request;
import com.example.gateway.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
public interface GatewayOrderService {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/orderProduct", produces = "application/json")
    Mono<Response> orderProduct(@RequestBody OrderRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/viewOrder", produces = "application/json")
    Mono<Response> viewOrder(@RequestBody OrderRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/viewProduct", produces = "application/json")
    Mono<Response> viewProduct(@RequestBody OrderRequest request);

}
