package org.example.springjwt.repository;

import jakarta.transaction.Transactional;
import org.example.springjwt.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update CategoryEntity set nameEng=:nameEng,nameRu=:nameRu,nameUz=:nameUz,orderNumber=:orderNumber,updateDate=:updateDate where id=:id")
    Integer update(String nameEng, String nameRu, String nameUz, String orderNumber, LocalDateTime updateDate,Integer id);

    @Transactional
    @Modifying
    @Query("delete CategoryEntity where id=:id")
    Integer deleteAllById(Integer id);

    @Query("select c from CategoryEntity c")
    Page<CategoryEntity> findByPage(Pageable pageable);

    @Query("select c from CategoryEntity c where c.visible=true")
    List<CategoryEntity>findAll();
}
