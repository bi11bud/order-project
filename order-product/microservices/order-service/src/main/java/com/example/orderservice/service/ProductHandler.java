package com.example.orderservice.service;

import com.example.orderservice.dto.Response;
import com.example.orderservice.dto.Status;
import com.example.orderservice.dto.content.ProductRequest;
import com.example.orderservice.dto.content.ProductResponse;
import com.example.orderservice.persistance.model.CategoryEntity;
import com.example.orderservice.persistance.model.ProductEntity;
import com.example.orderservice.persistance.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductHandler {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private final static ObjectMapper mapper = new ObjectMapper();

    public Response<ProductResponse[]> viewProducts(ProductRequest request) {
        Status status = new Status();
        status.setStatusCode("0000");
        status.setStatusDesc("OK");

        Response<ProductResponse[]> response = new Response<>();
        response.setStatus(status);

        List<ProductEntity> listProductEnt = new ArrayList<>();
        List<ProductResponse> listProduct = new ArrayList<>();

        if(request.getProductId()==null || request.getProductId()==""){
            listProductEnt = this.productRepository.getAllProducts();
        }else{
            ProductEntity getProduct = this.productRepository.getByProductId(request.getProductId());
            if(getProduct==null){
                status.setStatusCode("9999");
                status.setStatusDesc("Product Id not found");
                response.setStatus(status);
                return response;
            }
            listProductEnt.add(getProduct);
        }

        try {
            log.info("List Product:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listProductEnt));
        } catch (Exception ex) {
        }

        for (ProductEntity prod : listProductEnt) {
            ProductResponse p = new ProductResponse();
            p.setProductId(prod.getProductId());
            p.setProductName(prod.getProductName());
            p.setPrice(prod.getPrice());
            p.setQuantity(prod.getQuantity());
            p.setSize(prod.getSize());
            CategoryEntity category = this.categoryRepository.getByCategoryId(prod.getCategory());
            p.setCategory(category.getCategoryName());
            listProduct.add(p);
        }

        ProductResponse[] resultProduct = new ProductResponse[listProduct.size()];
        resultProduct = listProduct.toArray(resultProduct);
        response.setContent(resultProduct);

        return response;
    }
}
