package com.example.orderservice.persistance.repository;

import com.example.orderservice.persistance.model.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, String> {

    @Query(value = "select p from ProductEntity p where p.status = 'Y' order by p.productName asc")
    List<ProductEntity> getAllProducts();

    @Query(value = "select p from ProductEntity p where p.productId = :productId and p.status = 'Y'")
    ProductEntity getByProductId(@Param("productId") String productId);

}
