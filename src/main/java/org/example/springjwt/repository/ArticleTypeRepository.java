package org.example.springjwt.repository;

import jakarta.transaction.Transactional;
import org.example.springjwt.entity.ArticleTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity,Integer> {

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity set nameUz=:nameUz,nameEng=:nameEng,nameRu=:nameRu,updateDate=:updateDate where id=:id")
    Integer update(Integer id, String nameUz, String nameEng, String nameRu, LocalDateTime updateDate);
    @Transactional
    @Modifying
    @Query("delete ArticleTypeEntity where id=:id")

    Integer deleteAllById(Integer id);

    @Query("select c from ArticleTypeEntity c ")
    Page<ArticleTypeEntity> findByNamePattern(Pageable pageable);

    @Query("from ArticleTypeEntity where visible=true ")
    List<ArticleTypeEntity> findAllArticleType();


}
