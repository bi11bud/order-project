package com.example.orderservice.controller;

import com.example.orderservice.dto.Response;
import com.example.orderservice.dto.content.ProductRequest;
import com.example.orderservice.dto.content.ProductResponse;
import com.example.orderservice.service.ProductHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class ProductController {

    @Resource
    ProductHandler productHandler;

    private final static ObjectMapper mapper = new ObjectMapper();

    @ResponseStatus
    @PostMapping(value="/viewProducts", produces = "application/json")
    public Response<ProductResponse[]> viewProducts(@RequestBody ProductRequest request){

        Response<ProductResponse[]> response = this.productHandler.viewProducts(request);

        return response;
    }

}
