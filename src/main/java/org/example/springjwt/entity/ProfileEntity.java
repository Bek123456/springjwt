package org.example.springjwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.springjwt.enums.ProfileRole;
import org.example.springjwt.enums.ProfileStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "profile_entity")
public class ProfileEntity {
    //id,name,surname,email,phone,password,status,role,visible,created_date,photo_id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    @Column(unique = true,nullable = false)
    private String phone;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private ProfileStatus status=ProfileStatus.ACTIVE;
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
    private Boolean visible=true;
    private String email;
    private LocalDateTime createdDate=LocalDateTime.now();
    @Column(name = "photo_id")
    private String photoId;
    @ManyToOne()
    @JoinColumn(name = "photo_id",insertable = false,updatable = false)
    private AttachEntity photo;
    private LocalDateTime updateDate;
}
