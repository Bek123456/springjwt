package org.example.springjwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "attache_entity")
public class AttachEntity {

    //id (uuid), original_name, path, size, extension, created_date

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdDate=LocalDateTime.now();
}
