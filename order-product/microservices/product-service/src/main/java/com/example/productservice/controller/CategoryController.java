package com.example.productservice.controller;

import com.example.productservice.dto.Response;
import com.example.productservice.dto.content.CategoryRequest;
import com.example.productservice.dto.content.CategoryResponse;
import com.example.productservice.dto.content.ProductRequest;
import com.example.productservice.dto.content.ProductResponse;
import com.example.productservice.services.CategoryHandler;
import com.example.productservice.services.ProductHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@Slf4j
public class CategoryController {

    @Resource
    CategoryHandler categoryHandler;

    private final static ObjectMapper mapper = new ObjectMapper();

    @ResponseStatus
    @PostMapping(value="/viewCategories", produces = "application/json")
    public Response<CategoryResponse[]> viewCategories(@RequestBody CategoryRequest request){

        Response<CategoryResponse[]> response = this.categoryHandler.viewCategories(request);

        return response;
    }
}
