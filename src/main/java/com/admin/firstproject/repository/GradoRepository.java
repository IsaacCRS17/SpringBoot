package com.admin.firstproject.repository;

import com.admin.firstproject.Entity.GradoEntity;
import com.admin.firstproject.Entity.SeccionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradoRepository extends JpaRepository<GradoEntity, Integer> {
    @Query(value = "select c from GradoEntity c " +
            "where c.status = :status " +
            "and (c.code like concat('%', :filter, '%') or c.name like concat('%', :filter, '%'))"+
            "order by c.name")
    Optional<List<GradoEntity>> findGrados(String status, String filter, Pageable pageable);

    @Query(value = "select count(c) from GradoEntity c " +
            "where c.status = :status " +
            "and (c.code like concat('%', :filter, '%') or c.name like concat('%', :filter, '%'))"+
            "order by c.name")
    Long findCountGrados(String status, String filter);

    Optional<GradoEntity> findByUniqueIdentifier(String uniqueIdentifier);
    Optional<GradoEntity> findByName(Character name);

}
