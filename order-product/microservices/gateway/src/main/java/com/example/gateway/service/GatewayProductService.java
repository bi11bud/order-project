package com.example.gateway.service;

import com.example.gateway.dto.CategoryRequest;
import com.example.gateway.dto.ProductRequest;
import com.example.gateway.dto.Request;
import com.example.gateway.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@CrossOrigin
@RestController
public interface GatewayProductService {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/addProduct", produces = "application/json")
    Mono<Response> addProduct(@RequestBody ProductRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/updateProduct", produces = "application/json")
    Mono<Response> updateProduct(@RequestBody ProductRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/deleteProduct", produces = "application/json")
    Mono<Response> deleteProduct(@RequestBody ProductRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/viewProductAdmin", produces = "application/json")
    Mono<Response> viewProductAdmin(@RequestBody ProductRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(value = "/viewCategories", produces = "application/json")
    Mono<Response> viewCategories(@RequestBody CategoryRequest request);


}
