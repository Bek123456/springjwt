package org.example.springjwt.repository;

import org.example.springjwt.entity.AttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachRepository extends JpaRepository<AttachEntity, UUID> {

}
