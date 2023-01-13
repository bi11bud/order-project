package com.example.productservice.controller;

import com.example.productservice.dto.Request;
import com.example.productservice.dto.Response;
import com.example.productservice.dto.content.ProductRequest;
import com.example.productservice.dto.content.ProductResponse;
import com.example.productservice.services.ProductHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@Slf4j
public class ProductController {

    @Resource
    ProductHandler productHandler;

    private final static ObjectMapper mapper = new ObjectMapper();

    @ResponseStatus
    @PostMapping(value="/addProduct", produces = "application/json")
    public Response<ProductResponse> addProduct(@RequestBody ProductRequest request){

        Response<ProductResponse> response = this.productHandler.addProduct(request);

        return response;
    }

    @ResponseStatus
    @PostMapping(value="/updateProduct", produces = "application/json")
    public Response<ProductResponse> updateProduct(@RequestBody ProductRequest request){

        Response<ProductResponse> response = this.productHandler.updateProduct(request);

        return response;
    }

    @ResponseStatus
    @PostMapping(value="/viewProducts", produces = "application/json")
    public Response<ProductResponse[]> viewProducts(@RequestBody ProductRequest request){

        Response<ProductResponse[]> response = this.productHandler.viewProducts(request);

        return response;
    }

    @ResponseStatus
    @PostMapping(value="/deleteProduct", produces = "application/json")
    public Response<ProductResponse> deleteProduct(@RequestBody ProductRequest request){

        Response<ProductResponse> response = this.productHandler.deleteProduct(request);

        return response;
    }
}
