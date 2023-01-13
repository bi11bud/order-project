package com.example.orderservice.controller;

import com.example.orderservice.dto.Response;
import com.example.orderservice.dto.content.OrderRequest;
import com.example.orderservice.dto.content.OrderResponse;
import com.example.orderservice.dto.content.ProductRequest;
import com.example.orderservice.dto.content.ProductResponse;
import com.example.orderservice.service.OrderHandler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class OrderController {

    @Resource
    OrderHandler orderHandler;

    @ResponseStatus
    @PostMapping(value="/orderProduct", produces = "application/json")
    public Response<OrderResponse> orderProduct(@RequestBody OrderRequest request){

        Response<OrderResponse> response = this.orderHandler.orderProduct(request);

        return response;
    }

    @ResponseStatus
    @PostMapping(value="/viewOrder", produces = "application/json")
    public Response<OrderResponse[]> viewOrder(@RequestBody OrderRequest request){

        Response<OrderResponse[]> response = this.orderHandler.viewOrder(request);

        return response;
    }

}
