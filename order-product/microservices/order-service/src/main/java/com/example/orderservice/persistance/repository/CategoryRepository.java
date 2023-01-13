package com.example.orderservice.persistance.repository;

import com.example.orderservice.persistance.model.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity,String> {

    @Query(value = "select p from CategoryEntity p order by cast(p.categoryId as int) asc")
    List<CategoryEntity> getAllCategories();

    @Query(value = "select p from CategoryEntity p where p.categoryId = :categoryId")
    CategoryEntity getByCategoryId(@Param("categoryId") String categoryId);

    @Query(value = "select p from CategoryEntity p where p.categoryName = :categoryName")
    CategoryEntity getByCategoryName(@Param("categoryName") String categoryName);
}
