package com.admin.firstproject.repository;

import com.admin.firstproject.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Query(value = "select c from CategoryEntity c " +
            "where c.status = :status " +
            "and (c.code like concat('%', :filter, '%') or c.name like concat('%', :filter, '%'))"+
            "order by c.name")
    Optional<List<CategoryEntity>> findCategories(String status, String filter);
    Optional<CategoryEntity> findByUniqueIdentifier(String uniqueIdentifier);

    Optional<CategoryEntity> findByName(String name);

}
