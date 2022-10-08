package com.admin.firstproject.repository;

import com.admin.firstproject.Entity.SeccionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeccionRepository extends JpaRepository<SeccionEntity, Integer> {
    @Query(value = "select c from SeccionEntity c " +
            "where c.status = :status " +
            "and (c.code like concat('%', :filter, '%') or c.name like concat('%', :filter, '%'))"+
            "order by c.name")
    Optional<List<SeccionEntity>> findSeccion(String status, String filter, Pageable pageable);

    @Query(value = "select count(c) from SeccionEntity c " +
            "where c.status = :status " +
            "and (c.code like concat('%', :filter, '%') or c.name like concat('%', :filter, '%'))"+
            "order by c.name")
    Long findCountSeccion(String status, String filter);
    Optional<SeccionEntity> findByUniqueIdentifier(String uniqueIdentifier);
    Optional<SeccionEntity> findByName(Character name);
}
