package org.example.springjwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.springjwt.entity.AttachEntity;
import org.example.springjwt.enums.ProfileRole;
import org.example.springjwt.enums.ProfileStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    //id,name,surname,email,phone,password,status,role,visible,created_date,photo_id
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String password;
    private ProfileRole role;
    private Boolean visible=true;
    private LocalDateTime createdDate=LocalDateTime.now();
    private String photoId;
    private LocalDateTime updatedDate;
}
