package org.example.springjwt.repository;

import jakarta.transaction.Transactional;
import org.example.springjwt.entity.ArticleTypeEntity;
import org.example.springjwt.entity.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update RegionEntity  set nameUz=:nameUz,nameEng=:nameEng,nameRu=:nameRu,updateDate=:updateDate where id=:id")
    Integer update(Integer id, String nameUz, String nameEng, String nameRu, LocalDateTime updateDate);
    @Transactional
    @Modifying
    @Query("delete RegionEntity where id=:id")

    Integer deleteAllById(Integer id);

    @Query("select c from RegionEntity  c ")
    Page<RegionEntity> findByNamePattern(Pageable pageable);

    @Query("from RegionEntity where visible=true ")
    List<RegionEntity> findAllArticleType();


}
