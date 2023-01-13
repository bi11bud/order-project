package com.example.orderservice.service;

import com.example.orderservice.dto.Response;
import com.example.orderservice.dto.Status;
import com.example.orderservice.dto.content.OrderRequest;
import com.example.orderservice.dto.content.OrderResponse;
import com.example.orderservice.persistance.model.OrderEntity;
import com.example.orderservice.persistance.model.ProductEntity;
import com.example.orderservice.persistance.repository.OrderRepository;
import com.example.orderservice.persistance.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderHandler {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    private final static ObjectMapper mapper = new ObjectMapper();

    public Response<OrderResponse> orderProduct(OrderRequest request) {
        Status status = new Status();
        status.setStatusCode("0000");
        status.setStatusDesc("OK");

        Response<OrderResponse> response = new Response<>();
        response.setStatus(status);

        ProductEntity product = this.productRepository.getByProductId(request.getProductId());
        if(product==null){
            status.setStatusCode("9999");
            status.setStatusDesc("Product Id not found");
            response.setStatus(status);
            return response;
        }

        int validQuantity = Integer.parseInt(product.getQuantity()) - Integer.parseInt(request.getQuantity()) ;

        log.info("sisa quantity: "+validQuantity);
        if(validQuantity<0){
            status.setStatusCode("9999");
            status.setStatusDesc("Invalid Amount of Quantity");
            response.setStatus(status);
            return response;
        }

        Date now = new Date();

        product.setQuantity(String.valueOf(validQuantity));
        product.setMby("order");
        product.setMdate(now);

        OrderEntity orderEnt = new OrderEntity();
        orderEnt.setOrderId(String.valueOf(this.orderRepository.getNextId()));
        orderEnt.setProductId(product.getProductId());
        orderEnt.setPrice(product.getPrice());
        orderEnt.setQuantity(request.getQuantity());
        orderEnt.setOrderName(request.getName());
        orderEnt.setCby("user");
        orderEnt.setCdate(now);
        orderEnt.setMby("user");
        orderEnt.setMdate(now);

        try{
            log.info("Order: \n"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderEnt));
            log.info("save product");
            productRepository.save(product);
            log.info("save order");
            orderRepository.save(orderEnt);
        }catch (Exception e){
            log.error("Failed save to database");
            status.setStatusCode("9999");
            status.setStatusDesc("Failed Save Order");
            response.setStatus(status);
            return response;
        }

        OrderResponse res = new OrderResponse();
        res.setOrderId(orderEnt.getOrderId());
        res.setProductId(product.getProductId());
        res.setName(orderEnt.getOrderName());
        res.setProductName(product.getProductName());
        res.setSize(product.getSize());
        res.setCategory(product.getCategory());
        res.setPrice(product.getPrice());
        res.setQuantity(orderEnt.getQuantity());
        int totalPrice = Integer.parseInt(orderEnt.getPrice())*Integer.parseInt(orderEnt.getQuantity());
        res.setTotalPrice(String.valueOf(totalPrice));

        response.setContent(res);

        return response;
    }

    public Response<OrderResponse[]> viewOrder(OrderRequest request) {
        Status status = new Status();
        status.setStatusCode("0000");
        status.setStatusDesc("OK");

        Response<OrderResponse[]> response = new Response<>();
        response.setStatus(status);

        List<OrderEntity> listOrderEnt = new ArrayList<>();
        List<OrderResponse> listOrder = new ArrayList<>();

        if((request.getOrderId()!=null && !request.getOrderId().equals(""))
                && (request.getProductId()==null || request.getProductId().equals(""))
                && (request.getName()==null || request.getName().equals(""))){
            OrderEntity order = this.orderRepository.getByOrderId(request.getOrderId());
            if(order!=null){
                listOrderEnt.add(order);
            }
        }
        else if((request.getOrderId()==null || request.getOrderId().equals(""))
                && (request.getProductId()!=null && !request.getOrderId().equals(""))
                && (request.getName()==null || request.getName().equals(""))){
            listOrderEnt = this.orderRepository.getByProductId(request.getProductId());
        }
        else if((request.getOrderId()==null || request.getOrderId().equals(""))
                && (request.getProductId()==null || request.getProductId().equals(""))
                && (request.getName()!=null && !request.getName().equals(""))){
            listOrderEnt = this.orderRepository.getByOrderName(request.getName().toUpperCase());
        }
        else {
            listOrderEnt = this.orderRepository.getAllOrders();
        }


            try {
            log.info("List Order:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOrderEnt));
        } catch (Exception ex) {
        }

        for (OrderEntity order : listOrderEnt) {
            OrderResponse o = new OrderResponse();
            o.setOrderId(order.getOrderId());
            o.setProductId(order.getProductId());
            o.setName(order.getOrderName());
            ProductEntity product = this.productRepository.getByProductId(order.getProductId());
            o.setProductName(product.getProductName());
            o.setSize(product.getSize());
            o.setCategory(product.getCategory());
            o.setPrice(order.getPrice());
            o.setQuantity(order.getQuantity());
            int totalPrice = Integer.parseInt(order.getPrice())*Integer.parseInt(order.getQuantity());
            o.setTotalPrice(String.valueOf(totalPrice));
            listOrder.add(o);
        }

        OrderResponse[] resultOrder = new OrderResponse[listOrder.size()];
        resultOrder = listOrder.toArray(resultOrder);
        response.setContent(resultOrder);

        return response;
    }
}
