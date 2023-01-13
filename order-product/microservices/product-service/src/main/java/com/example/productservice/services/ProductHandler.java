package com.example.productservice.services;

import com.example.productservice.dto.*;
import com.example.productservice.dto.content.ProductRequest;
import com.example.productservice.dto.content.ProductResponse;
import com.example.productservice.persistance.model.CategoryEntity;
import com.example.productservice.persistance.model.ProductEntity;
import com.example.productservice.persistance.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProductHandler {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private final static ObjectMapper mapper = new ObjectMapper();

    public Response<ProductResponse> addProduct(ProductRequest request){
        Status status = new Status();
        status.setStatusCode("0000");
        status.setStatusDesc("OK");

        Response<ProductResponse> response = new Response<>();
        response.setStatus(status);
        response.setContent(null);

        try{
            log.info("Request: \n"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
        }catch(Exception e){
        }

        if(request==null){
            status.setStatusCode("9999");
            status.setStatusDesc("Invalid Request");
            response.setStatus(status);
            return response;
        }

        ProductEntity checkName = this.productRepository.getByProductName(request.getProductName().toUpperCase());
        if(checkName!=null){
            status.setStatusCode("9999");
            status.setStatusDesc("Product Name Already Exist");
            response.setStatus(status);
            return response;
        }

        CategoryEntity category = this.categoryRepository.getByCategoryName(request.getCategory());
        if(category!=null){
            status.setStatusCode("9999");
            status.setStatusDesc("Invalid Category Name");
            response.setStatus(status);
            return response;
        }

        if(!(request.getSize().equalsIgnoreCase("XS") ||
                request.getSize().equalsIgnoreCase("S") ||
                request.getSize().equalsIgnoreCase("M") ||
                request.getSize().equalsIgnoreCase("L") ||
                request.getSize().equalsIgnoreCase("XL"))){
            status.setStatusCode("9999");
            status.setStatusDesc("Invalid Size Request");
            response.setStatus(status);
            return response;
        }

        Date now = new Date();

        ProductEntity product = new ProductEntity();
        product.setProductId(String.valueOf(this.productRepository.getNextId()));
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setSize(request.getSize().toUpperCase());
        product.setCategory(category.getCategoryId());
        product.setStatus("Y");
        product.setCby("admin");
        product.setCdate(now);
        product.setMby("admin");
        product.setMdate(now);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            log.error("Save Product Failed: \n", e);
            status.setStatusCode("9999");
            status.setStatusDesc("Save Failed");
            response.setStatus(status);
            return response;
        }

        return response;
    }

    public Response<ProductResponse> updateProduct(ProductRequest request) {
        Status status = new Status();
        status.setStatusCode("0000");
        status.setStatusDesc("OK");

        Response<ProductResponse> response = new Response<>();
        response.setStatus(status);

        ProductEntity p = this.productRepository.getByProductId(request.getProductId());
        if(p==null){
            status.setStatusCode("9999");
            status.setStatusDesc("Product Id not found");
            response.setStatus(status);
            return response;
        }

        ProductEntity checkName = this.productRepository.getByProductName(request.getProductName().toUpperCase());
        if(checkName!=null){
            if(!checkName.getProductId().equals(p.getProductId())){
                status.setStatusCode("9999");
                status.setStatusDesc("Product Name Already Exist");
                response.setStatus(status);
                return response;
            }
        }

        if(!(request.getStatus().equalsIgnoreCase("Y") ||
                (request.getStatus().equalsIgnoreCase("N")))){
            log.info("status: "+request.getStatus());
            status.setStatusCode("9999");
            status.setStatusDesc("Invalid Status Product Request");
            response.setStatus(status);
            return response;
        }

        CategoryEntity category = this.categoryRepository.getByCategoryName(request.getCategory());
        if(checkName!=null){
            status.setStatusCode("9999");
            status.setStatusDesc("Product Name Already Exist");
            response.setStatus(status);
            return response;
        }

        if(!(request.getSize().equalsIgnoreCase("XS") ||
                request.getSize().equalsIgnoreCase("S") ||
                request.getSize().equalsIgnoreCase("M") ||
                request.getSize().equalsIgnoreCase("L") ||
                request.getSize().equalsIgnoreCase("XL"))){
            status.setStatusCode("9999");
            status.setStatusDesc("Invalid Size Request");
            response.setStatus(status);
            return response;
        }

        Date now = new Date();

        p.setProductName(request.getProductName());
        p.setQuantity(request.getQuantity());
        p.setPrice(request.getPrice());
        p.setStatus(request.getStatus().toUpperCase());
        p.setSize(request.getSize().toUpperCase());
        p.setCategory(category.getCategoryName());
        p.setMdate(now);
        p.setMby("admin");
        try {
            productRepository.save(p);
        } catch (Exception e) {
            log.error("Update Product Failed: \n", e);
            status.setStatusCode("9999");
            status.setStatusDesc("Update Failed");
            response.setStatus(status);
            return response;
        }

        ProductResponse res = new ProductResponse();
        res.setProductName(p.getProductName());
        res.setQuantity(p.getQuantity());
        res.setPrice(p.getPrice());
        res.setStatus(p.getStatus().toUpperCase());

        response.setContent(res);

        return response;
    }

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
            p.setStatus(prod.getStatus());
            listProduct.add(p);
        }

        ProductResponse[] resultProduct = new ProductResponse[listProduct.size()];
        resultProduct = listProduct.toArray(resultProduct);
        response.setContent(resultProduct);

        return response;
    }

    public Response<ProductResponse> deleteProduct(ProductRequest request) {
        Status status = new Status();
        status.setStatusCode("0000");
        status.setStatusDesc("OK");

        Response<ProductResponse> response = new Response<>();
        response.setStatus(status);

        try {
            log.info("Request:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
        } catch (Exception ex) {
        }

        ProductEntity emp = this.productRepository.getByProductId(request.getProductId());
        if(emp==null){
            status.setStatusCode("9999");
            status.setStatusDesc("Product Id not found");
            response.setStatus(status);
            return response;
        }

        try {
            this.productRepository.deleteById(request.getProductId());
        } catch (Exception e) {
            log.error("Delete Product Failed: \n", e);
            status.setStatusCode("9999");
            status.setStatusDesc("Delete Failed");
            response.setStatus(status);
            return response;
        }

        return response;
    }

}
