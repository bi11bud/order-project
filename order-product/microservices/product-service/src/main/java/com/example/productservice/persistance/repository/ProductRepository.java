package com.example.productservice.persistance.repository;

import com.example.productservice.persistance.model.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, String> {

    @Query(value = "select p from ProductEntity p order by p.productName asc")
    List<ProductEntity> getAllProducts();

    @Query(value = "select p from ProductEntity p where p.productId = :productId")
    ProductEntity getByProductId(@Param("productId") String productId);

    @Query(value = "select p from ProductEntity p where UPPER(p.productName) = :productName")
    ProductEntity getByProductName(@Param("productName") String productName);

    @Query(value = "select nextval('product_id_seq')", nativeQuery = true)
    Long getNextId();
}
