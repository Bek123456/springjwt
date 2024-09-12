package org.example.springjwt.repository;

import org.example.springjwt.entity.ProfileEntity;
import org.example.springjwt.entity.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer> {
      @Query("select c from ProfileEntity c where c.id=:id")
    Optional<ProfileEntity>findById(Integer id);

    @Query("select c from ProfileEntity  c ")
    Page<ProfileEntity> findByNamePattern(Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete ProfileEntity c where c.id=:id")
    Integer deleteAllById(Integer id);


    @Transactional
    @Modifying
    @Query("update ProfileEntity  set photoId=:photoId where id=:id")
    Integer updateByIdAndPhoto(Integer id,String photoId);
}
