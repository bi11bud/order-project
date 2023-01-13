package com.example.orderservice.persistance.repository;

import com.example.orderservice.persistance.model.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {

    @Query(value = "select p from OrderEntity p order by p.cdate asc")
    List<OrderEntity> getAllOrders();

    @Query(value = "select p from OrderEntity p where p.orderId = :orderId")
    OrderEntity getByOrderId(@Param("orderId") String orderId);

    @Query(value = "select p from OrderEntity p where p.productId = :productId")
    List<OrderEntity> getByProductId(@Param("productId") String productId);

    @Query(value = "select p from OrderEntity p where UPPER(p.orderName) like %:orderName%")
    List<OrderEntity> getByOrderName(@Param("orderName") String orderName);

    @Query(value = "select nextval('order_id_seq')", nativeQuery = true)
    Long getNextId();

}
