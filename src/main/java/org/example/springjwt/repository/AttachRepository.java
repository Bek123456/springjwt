package org.example.springjwt.repository;

import jakarta.transaction.Transactional;
import org.example.springjwt.entity.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttachRepository extends JpaRepository<AttachEntity, String> {
  @Query("select c from AttachEntity c where c.id=:id")
  Optional<AttachEntity> findById(String id);

  List<AttachEntity> findByOriginalName(String originalName);
  @Query("select c from AttachEntity c")
  Page<AttachEntity> findByPage(Pageable pageable);

  @Transactional
  @Modifying
  @Query("delete AttachEntity c where c.id=:id")
  Integer deleteAllById(String id);
}
