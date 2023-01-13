package com.example.productservice.services;

import com.example.productservice.dto.Response;
import com.example.productservice.dto.Status;
import com.example.productservice.dto.content.CategoryRequest;
import com.example.productservice.dto.content.CategoryResponse;
import com.example.productservice.dto.content.ProductRequest;
import com.example.productservice.dto.content.ProductResponse;
import com.example.productservice.persistance.model.CategoryEntity;
import com.example.productservice.persistance.model.ProductEntity;
import com.example.productservice.persistance.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryHandler {

    @Autowired
    CategoryRepository categoryRepository;

    private final static ObjectMapper mapper = new ObjectMapper();

    public Response<CategoryResponse[]> viewCategories(CategoryRequest request) {
        Status status = new Status();
        status.setStatusCode("0000");
        status.setStatusDesc("OK");

        Response<CategoryResponse[]> response = new Response<>();
        response.setStatus(status);

        List<CategoryEntity> listCategoryEnt = this.categoryRepository.getAllCategories();
        List<CategoryResponse> listCategories = new ArrayList<>();

        for (CategoryEntity c : listCategoryEnt) {
            CategoryResponse res = new CategoryResponse();
            res.setCategoryId(c.getCategoryId());
            res.setCategoryName(c.getCategoryName());
            listCategories.add(res);
        }

        CategoryResponse[] resultCategories = new CategoryResponse[listCategories.size()];
        resultCategories = listCategories.toArray(resultCategories);
        response.setContent(resultCategories);

        return response;
    }

}
