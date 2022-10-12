package com.admin.firstproject.repository;

import com.admin.firstproject.Entity.SeccionEntity;
import com.admin.firstproject.util.ConstantsGeneric;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeccionRepository extends JpaRepository<SeccionEntity, Integer> {
    @Query(value = "select s from SeccionEntity s " +
            "where s.status = :status " +
            "and (s.code like concat('%', :filter, '%') or s.name like concat('%', :filter, '%'))"+
            "order by s.name")
    Optional<List<SeccionEntity>> findSeccion(String status, String filter, Pageable pageable);

    @Query(value = "select count(s) from SeccionEntity s " +
            "where s.status = :status " +
            "and (s.code like concat('%', :filter, '%') or s.name like concat('%', :filter, '%'))"+
            "order by s.name")
    Long findCountSeccion(String status, String filter);
    Optional<SeccionEntity> findByUniqueIdentifier(String uniqueIdentifier);
    Optional<SeccionEntity> findByName(Character name);

    @Query(value = "SELECT count(s) FROM GradoEntity g JOIN g.secciones s " +
            "WHERE g.uniqueIdentifier = :id " +
            "and s.status = :status " +
            "and (s.code like concat('%', :filter, '%') or s.name like concat('%', :filter, '%'))" +
            "order by s.name")
    Long findCountSeccionxGrado(String id, String status, String filter);

    @Query(value = "SELECT s FROM GradoEntity g JOIN g.secciones s " +
            "WHERE g.uniqueIdentifier = :id " +
            "and s.status = :status " +
            "and (s.code like concat('%', :filter, '%') or s.name like concat('%', :filter, '%'))" +
            "order by s.name")
    Optional<List<SeccionEntity>> findSeccionxGrado(String id, String status, String filter, Pageable pageable);
}
